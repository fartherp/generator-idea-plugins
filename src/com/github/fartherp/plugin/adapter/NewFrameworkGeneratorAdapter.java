/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.adapter;

import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.framework.common.util.FileUtilies;
import com.github.fartherp.generatorcode.framework.FrameworkGenerator;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/16
 */
public class NewFrameworkGeneratorAdapter extends FrameworkGenerator implements GeneratorAdapter {

    private String pojoPath;

    private String daoPath;

    private String servicePath;

    private String xmlPath;

    public NewFrameworkGeneratorAdapter(String pojoPath, String daoPath, String servicePath, String xmlPath, CodeGenContext context) {
        this.pojoPath = pojoPath;
        this.daoPath = daoPath;
        this.servicePath = servicePath;
        this.xmlPath = xmlPath;
        this.context = context;
    }

    @Override
    protected void write() {
        for (GeneratedJavaFile gxf : generatedJavaFiles) {
            String module = gxf.getModule();
            if ("dao".equals(module)) {
                // 目标文件
                File targetFile = FileUtilies.getDirectory("", gxf.getFileName(), daoPath);
                // 目标内容
                String source = gxf.getFormattedContent();
                if (!targetFile.exists()) {
                    // 输出流写文件
                    FileUtilies.writeFile(targetFile, source, "UTF-8");
                }
            } else if ("service".equals(module)) {
                String targetPackage = "";
                if (gxf.getTargetPackage().contains("impl")) {
                    targetPackage = "impl";
                }
                // 目标文件
                File targetFile = FileUtilies.getDirectory(targetPackage, gxf.getFileName(), servicePath);
                // 目标内容
                String source = gxf.getFormattedContent();
                if (!targetFile.exists()) {
                    // 输出流写文件
                    FileUtilies.writeFile(targetFile, source, "UTF-8");
                }
            } else if ("bean".equals(module)) {
                // 目标文件
                File targetFile = FileUtilies.getDirectory("", gxf.getFileName(), pojoPath);
                // 目标内容
                String source = gxf.getFormattedContent();
                // 输出流写文件
                FileUtilies.writeFile(targetFile, source, "UTF-8");
            }
        }

        for (GeneratedXmlFile gxf : generatedXmlFiles) {
            String fileName = gxf.getFileName();
            File targetFile = FileUtilies.getDirectory("", fileName, xmlPath);
            // 目标内容
            String source = gxf.getFormattedContent();
            if (!fileName.startsWith("Base") && targetFile.exists()) {
                // 输出流写文件
                continue;
            }
            FileUtilies.writeFile(targetFile, source, "UTF-8");
        }
    }

    @Override
    protected void createDir() {
//        GeneratorAdapterUtils.createDir(context, path);
    }

    @Override
    public void generateAdapter() {
        build(context).generate();
    }
}
