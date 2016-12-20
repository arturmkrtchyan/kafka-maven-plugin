package com.arturmkrtchyan.kafka;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Conf
{
    private final Path config;

    public Conf(Path config)
    {
        this.config = config;
    }

    public static Conf fromPath(Path config)
    {
        return new Conf(config);
    }

    public Conf merge(String key, String value)
    {
        Map props = new HashMap();
        props.put(key, value);

        return merge(props);
    }

    public Conf merge(Map otherProps)
    {
        if(otherProps == null || otherProps.isEmpty())
        {
            return this;
        }

        try
        {
            Properties result = load();

            result.putAll(otherProps);

            save(result);
        }
        catch (IOException e)
        {
            throw new KafkaPluginException(String.format("Unable to update configuration file: %s", config.toAbsolutePath()), e);
        }

        return this;
    }

    private Properties load() throws IOException
    {
        Properties result = new Properties();

        FileInputStream fis = new FileInputStream(config.toFile());

        result.load(fis);

        fis.close();

        return result;
    }

    private void save(Properties serverProps) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(config.toFile());

        serverProps.store(fos, null);

        fos.close();
    }
}
