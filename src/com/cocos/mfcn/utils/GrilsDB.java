package com.cocos.mfcn.utils;

import com.cocos.mfcn.models.Gril;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrilsDB {
    private volatile static GrilsDB _myInstance;
    private String dbName;
    private String charset = "UTF-8";
    private File file;

    private OutputStream outputStream;
    private InputStream inputStream;

    public  static GrilsDB shareInstance(final String dbName)
    {
        if (null == _myInstance)
        {
            synchronized (GrilsDB.class){
                _myInstance = new GrilsDB(dbName);
            }
        }

        return _myInstance;
    }

    private GrilsDB(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 插入数据
     * @param key
     * @param data
     * @return
     */
    public String insert(String key ,String data)
    {
        try {
            File insertFile = new File(this.makeDBPath(key));
            OutputStream outputStream = out(insertFile);
            IOUtils.write(data, outputStream, this.charset);
            return insertFile.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            release();
        }
        return null;
    }

    public Map<String, String> selectAll()
    {
        if (null != this.file &&
                this.file.exists() &&
                this.file.isDirectory())
        {
            File[] files = this.file.listFiles();
            if (null != files && 0 < files.length)
            {
                Map<String, String> all = new HashMap<>();
                for (int i = 0; i < files.length; i ++)
                {
                    File file = files[i];
                    all.put(file.getName(), this.select(file.getName().replace(".json", "")));
                }
                return all;
            }
        }
        return null;
    }

    public String select(String key)
    {
        File queryFile = new File(this.makeDBPath(key));
        if (queryFile.exists() &&
                queryFile.isFile())
        {
            try {
                in(queryFile);
                byte[] datas = IOUtils.readFully(this.inputStream, (int) queryFile.length());
                return new String(datas, this.charset);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                release();
            }
        }
        return null;
    }

    public String delete(String key)
    {
        File queryFile = new File(this.makeDBPath(key));
        if (queryFile.exists() &&
                queryFile.isFile())
        {
            if(queryFile.delete())
            {
                return queryFile.getPath();
            }
        }
        return null;
    }

    public boolean deleteAll()
    {
        boolean result = true;
        if (null != this.file)
        {
            File[] files = this.file.listFiles();
            if (null !=files && 0 < files.length)
            {
                for (File file : files)
                {
                    result = file.delete();
                    if (!result)
                        break;
                }
            }
        }
        return result;
    }

    private String makeDBPath(String key)
    {
        return this.file.getPath()+"/" + key +".json";
    }


    public void init()
    {
        if (null == this.file)
        {
            this.file = new File("datas/" + this.dbName);
        }
        this.autoCreate(file);
    }

    private void autoCreate(final File file)
    {
        if (!file.exists())
        {
            file.mkdirs();
        }
    }

    private void autoCreateFile(final File file) throws IOException {
        if (!file.exists() &&
                file.isFile())
        {
            file.createNewFile();
        }
    }

    private OutputStream out(final File file) throws FileNotFoundException {
        if (null == this.outputStream ) {
            try {
                this.autoCreateFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.outputStream = new FileOutputStream(file);
        }
        return this.outputStream;
    }

    private InputStream in(final File file) throws FileNotFoundException {
        if (null == this.inputStream)
        {
            try {
                this.autoCreateFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.inputStream = new FileInputStream(file);
        }
        return this.inputStream;
    }

    private void release()
    {
        if (null != outputStream)
        {
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.outputStream = null;
        }

        if (null != inputStream)
        {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                this.inputStream = null;
            }
        }
    }
}
