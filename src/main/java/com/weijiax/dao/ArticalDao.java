package com.weijiax.dao;

import com.weijiax.helper.ConfigHelper;
import com.weijiax.util.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;

public class ArticalDao {

    private Connection connection;

    public ArticalDao(Connection connection){
        this.connection = connection;
    }

    public String getArtical(int articalId){
        String artical = null;
        try {
            InputStream stream = new FileInputStream(new File(ConfigHelper.getArticalDir()+"/"+articalId));
            artical = StreamUtil.readStream(stream);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return artical;
    }
}
