package com.example.foody;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

public class Networking {

    public static class URLRequestToHashmap implements Callable<HashMap> {
        String stringUrl;

        public URLRequestToHashmap(String stringUrl) {
            this.stringUrl = stringUrl;
        }

        @Override
        public HashMap call() throws Exception {
            try {
                HashMap map = new HashMap<>();
                URL url = new URL(stringUrl);
                URLConnection con = url.openConnection();
                InputStream in = con.getInputStream();
                map = new ObjectMapper().readValue(in, HashMap.class);
                return map;
            } catch (IOException e) {
                Log.e("JsonReaderParserException", e.toString());
                return null;
            }
        }
    }

    public static class URLRequestToArrayListHashmap implements Callable<ArrayList> {
        String stringUrl;

        public URLRequestToArrayListHashmap(String stringUrl) {
            this.stringUrl = stringUrl;
        }

        @Override
        public ArrayList call() throws Exception {
            try {
                URL url = new URL(stringUrl);
                URLConnection con = url.openConnection();
                InputStream in = con.getInputStream();

                LinkedHashMap linkedHashMap = new ObjectMapper().readValue(in, LinkedHashMap.class);
                ArrayList<HashMap> values = new ArrayList<ArrayList<HashMap>>(linkedHashMap.values()).get(0);

                return values;
            } catch (IOException e) {
                Log.e("JsonReaderParserException", e.toString());
                return null;
            }
        }
    }
}