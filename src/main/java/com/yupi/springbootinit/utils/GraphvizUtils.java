package com.yupi.springbootinit.utils;

import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.Parser;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

public class GraphvizUtils {
    public static void generateGraphviz(String graphvizFile) {
        try {
            MutableGraph g =  new Parser().read(ResourceUtils.getFile("classpath:test.dot"));
            g.graphAttrs().add()
                    .nodeAttrs().add(Font.name("FangSong"))
                    .graphAttrs().add(Font.name("FangSong"))
                    .linkAttrs().add(Font.name("FangSong"));
            Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex4-1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        generateGraphviz(null);
    }
}


