package tuco.org.barcode;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Properties;

public class AssetPropertyManager{

    private static final String DISCOGS_USER_AGENT = "discogs_useragent";
    private static final String DISCOGS_TOKEN = "discogs_token";

    private static final String PROP_FILE = "config.properties";
    private final Properties properties = new Properties();

    public AssetPropertyManager(Context context){
        AssetManager assetManager=context.getAssets();
        try{
            properties.load(assetManager.open(PROP_FILE));
        }catch(IOException ignored){
            // TODO: Handle error
        }
    }

    public String getDiscogsUserAgent(){
        return properties.getProperty(DISCOGS_USER_AGENT);
    }

    public String getDiscogsToken(){
        return properties.getProperty(DISCOGS_TOKEN);
    }
}