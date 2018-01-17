/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.plugin.dao;

import com.github.fartherp.framework.common.util.DbManager;
import com.github.fartherp.plugin.config.CodeConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/15
 */
public class CodeDao {
    public static List<String> selectTableName(CodeConfig config) {
        List<String> list = new ArrayList<String>();
        DbManager dbManager = new DbManager(config.getUrl(), config.getUser(), config.getPassword());
        Connection connection = dbManager.getConnection();
        String selectSql = "SELECT * FROM `information_schema`.`TABLES` t WHERE t.`TABLE_SCHEMA` = ?";
        PreparedStatement preparedStatement = dbManager.getPreparedStatement(connection, selectSql);
        ResultSet resultSet = null;
        try {
            String url = config.getUrl();
            String database = url.substring(url.lastIndexOf("/") + 1, url.length());
            preparedStatement.setString(1, database);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.close(connection, preparedStatement, resultSet);
        }
        return list;
    }
}
