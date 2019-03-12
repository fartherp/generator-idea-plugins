/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.plugin.adapter;

import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.framework.common.util.FileUtilies;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/16
 */
public class GeneratorAdapterUtils {

    public static void write(List<GeneratedJavaFile> generatedJavaFiles, List<GeneratedXmlFile> generatedXmlFiles, CodeGenContext context) {
        for (GeneratedJavaFile gxf : generatedJavaFiles) {
            // 目标文件
            File targetFile = FileUtilies.getDirectory("src.main.java." + gxf.getTargetPackage(), gxf.getFileName(), context.getOut());
            // 目标内容
            String source = gxf.getFormattedContent();
            // 输出流写文件
            FileUtilies.writeFile(targetFile, source, "UTF-8");
        }

        for (GeneratedXmlFile gxf : generatedXmlFiles) {
            // 目标文件
            File targetFile = FileUtilies.getDirectory("src.main.resources.mybatis", gxf.getFileName(), context.getOut());
            // 目标内容
            String source = gxf.getFormattedContent();
            // 输出流写文件
            FileUtilies.writeFile(targetFile, source, "UTF-8");
        }
    }

    public static void createDir(CodeGenContext context, String path) {
        context.setOut(path);
        File file = new File(context.getOut());
        try {
            FileUtils.forceMkdir(file);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        context.setFile(file);
    }
}
