/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.plugin.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/15
 */
@State(name = "CodeConfig", storages = {@Storage("codeConfig.xml")})
public class CodeConfig implements PersistentStateComponent<CodeConfig> {

    private String url;

    private String user;

    private String password;

    private String projectName;

    private String classpath;

    private Integer transactionType;

    private String transactionName;

    private Integer isColumnNameDelimited;

    private Set<String> tableNames;

    private String generatorType;

    private String pojoPath;

    private String daoPath;

    private String servicePath;

    private String xmlPath;

    private String host;

    private String port = "3306";

    private String database;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Integer getIsColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setIsColumnNameDelimited(Integer isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
    }

    public Set<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(Set<String> tableNames) {
        this.tableNames = tableNames;
    }

    public String getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(String generatorType) {
        this.generatorType = generatorType;
    }

    public String getPojoPath() {
        return pojoPath;
    }

    public void setPojoPath(String pojoPath) {
        this.pojoPath = pojoPath;
    }

    public String getDaoPath() {
        return daoPath;
    }

    public void setDaoPath(String daoPath) {
        this.daoPath = daoPath;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    @Nullable
    public CodeConfig getState() {
        return this;
    }

    @Override
    public void loadState(CodeConfig codeConfig) {
        XmlSerializerUtil.copyBean(codeConfig, this);
    }

    @Nullable
    public static CodeConfig getInstance(Project project) {
        return ServiceManager.getService(project, CodeConfig.class);
    }
}
