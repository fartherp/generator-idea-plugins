/*
 * Copyright (c) 2018. juzhen.io. All rights reserved.
 */

package com.github.fartherp.plugin.adapter;

import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.framework.common.util.FileUtilies;
import com.github.fartherp.generatorcode.framework.FrameworkGenerator;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/16
 */
public class FrameworkGeneratorAdapter extends FrameworkGenerator implements GeneratorAdapter {

    private String path;

    public FrameworkGeneratorAdapter(String path, CodeGenContext context) {
        this.path = path;
        this.context = context;
    }

    protected void write() {
        StringBuilder sb = new StringBuilder();
        for (GeneratedJavaFile gxf : generatedJavaFiles) {
            String module = gxf.getModule();
            if (!StringUtils.isBlank(module)) {
                sb.append(module);
                sb.append('.');
            }
            sb.append("src.main.java.");
            sb.append(gxf.getTargetPackage());
            // 目标文件
            File targetFile = FileUtilies.getDirectory(sb.toString(), gxf.getFileName(), context.getOut());
            // 目标内容
            String source = gxf.getFormattedContent();
            // 输出流写文件
            FileUtilies.writeFile(targetFile, source, "UTF-8");
            sb.setLength(0);
        }

        for (GeneratedXmlFile gxf : generatedXmlFiles) {
            // 目标文件
            String module = gxf.getModule();
            if (!StringUtils.isBlank(module)) {
                sb.append(module);
                sb.append('.');
            }
            sb.append("src.main.resources.mybatis");
            File targetFile = FileUtilies.getDirectory(sb.toString(), gxf.getFileName(), context.getOut());
            // 目标内容
            String source = gxf.getFormattedContent();
            // 输出流写文件
            FileUtilies.writeFile(targetFile, source, "UTF-8");
            sb.setLength(0);
        }
    }

    protected void createDir() {
        GeneratorAdapterUtils.createDir(context, path);
    }

    public void generateAdapter() {
        build(context).generate();
    }
}
