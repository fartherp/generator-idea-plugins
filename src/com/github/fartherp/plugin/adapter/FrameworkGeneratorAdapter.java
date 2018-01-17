/*
 * Copyright (c) 2018. juzhen.io. All rights reserved.
 */

package com.github.fartherp.plugin.adapter;

import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.generatorcode.framework.FrameworkGenerator;

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
        GeneratorAdapterUtils.write(generatedJavaFiles, generatedXmlFiles, context);
    }

    protected void createDir() {
        GeneratorAdapterUtils.createDir(context, path);
    }

    public void generateAdapter() {
        build(context).generate();
    }
}
