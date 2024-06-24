package com.example.foodydbms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @GetMapping("/signup")
    public UserDataValidation siugnup(@RequestParam(value = "email") String email,
                                              @RequestParam(value = "password") String password) {
        UserDataValidation callback = new UserDataValidation();

        try {
            String query = String.format("SELECT * FROM users WHERE email='%s'", email);
            PreparedStatement pst = PostgresConnection.connection.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.isBeforeFirst()) {
                callback.setEmail(false);
            }
            else callback.setEmail(true);

            if (password.length() < 4 || password.length() > 20) {
                callback.setPassword(false);
            }
            else callback.setPassword(true);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        if (callback.getEmail() && callback.getPassword()) {
            try {
                String query = String.format("INSERT INTO users(id, email, password, address) VALUES(DEFAULT, '%s', '%s', null)", email, password);
                PreparedStatement pst = PostgresConnection.connection.prepareStatement(query);
                pst.executeUpdate();
                pst.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return callback;
    }

    @GetMapping("/login")
    UserDataValidation login(@RequestParam(value = "email") String email,
                             @RequestParam(value = "password") String password) {
        try {
            String query = String.format("SELECT * FROM users WHERE email='%s'", email);
            PreparedStatement pst = PostgresConnection.connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                pst.close();
                rs.close();
                return new UserDataValidation(false, true);
            }
            if (rs.getString("password").equals(password)) {
                pst.close();
                rs.close();
                return new UserDataValidation(true, true);
            }
            pst.close();
            rs.close();
            return new UserDataValidation(true, false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/menu")
    Menu getMenu() {
        try {
            ArrayList array = new ArrayList<>();

            String query = "SELECT * FROM products";
            PreparedStatement pst = PostgresConnection.connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            HashMap map;

            while (rs.next()) {
                map = new HashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("name", rs.getString("name"));
                map.put("image", rs.getBytes("image"));
                map.put("description", rs.getString("description"));
                map.put("weight", rs.getInt("weight"));
                map.put("price", rs.getInt("price"));
                map.put("discount", rs.getBoolean("discount"));
                array.add(map);
            }

            return new Menu(array);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/special-offers")
    SpecialOffers getSpecialOffers() {
        try {
            ArrayList array = new ArrayList<>();

            String query = "SELECT * FROM special_offers";
            PreparedStatement pst = PostgresConnection.connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            HashMap map;

            while (rs.next()) {
                map = new HashMap<>();
                map.put("id", rs.getInt("id"));
                map.put("description", rs.getString("description"));
                map.put("image", rs.getBytes("image"));
                array.add(map);
            }

            return new SpecialOffers(array);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Can upload byte arrays of images
//    @GetMapping("/test")
//    void aa(){
//        try {
//            int c=1;
//
//            String query = "SELECT id,image_url,image FROM special_offers";
//            PreparedStatement pst = PostgresConnection.connection.prepareStatement(query);
//            ResultSet rs = pst.executeQuery();
//
//            while(rs.next()) {
//                URL url = new URL(rs.getString("image_url"));
//                InputStream in = new BufferedInputStream(url.openStream());
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                byte[] buf = new byte[1024];
//                int n = 0;
//                while (-1 != (n = in.read(buf))) {
//                    out.write(buf, 0, n);
//                }
//                out.close();
//                in.close();
//                byte[] response = out.toByteArray();
//
//                String query2 = String.format("UPDATE special_offers SET image = ? WHERE id = %d", rs.getInt("id"));
//                PreparedStatement pst2 = PostgresConnection.connection.prepareStatement(query2);
//                pst2.setBytes(1,response);
//                pst2.executeUpdate();
//
//                c++;
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    private String getByteArrayFromImageURL(String url) {

        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return null;
    }

    public byte[] recoverImageFromUrl(String urlText) throws Exception {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }

        return output.toByteArray();
    }

    String getImage() throws IOException {
        File image = new File("C:\\Users\\Franzenn\\Documents\\Projects\\Java\\foodydbms\\src\\main\\java\\com\\example\\foodydbms\\andrej-gajduljan.jpg");
        return Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()));
    }
}

class UserDataValidation {
    private boolean email;
    private boolean password;

    public UserDataValidation(boolean email, boolean password) {
        this.email = email;
        this.password = password;
    }

    public UserDataValidation() {}

    public boolean getEmail() {
        return email;
    }

    public boolean getPassword() {
        return password;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public void setPassword(boolean password) {
        this.password = password;
    }
}

class Menu {
    private ArrayList menu;

    public Menu(ArrayList menu) {
        this.menu = menu;
    }

    public ArrayList getMenu() {
        return menu;
    }

    public void setMenu(ArrayList menu) {
        this.menu = menu;
    }
}

class SpecialOffers {
    private ArrayList specialOffers;

    public SpecialOffers(ArrayList specialOffers) {
        this.specialOffers = specialOffers;
    }

    public ArrayList getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(ArrayList specialOffers) {
        this.specialOffers = specialOffers;
    }
}