package cn.glenn.concurrent.balkingpattern;

import javax.swing.text.html.Option;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

/**
 * @author: glenn wang
 * @date: 2019-11-27 11:02
 **/
public class Data {

    private String filename;

    private String content;

    private boolean changed;

    public Data(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.changed = true;
    }

    public synchronized void change(String newContent) {
        this.content = newContent;
        this.changed = true;
    }

    public synchronized void save() throws IOException {
        if (!changed) {
            return;
        }
        doSave();
        changed = false;
    }

    private void doSave() throws IOException {
        Optional.of(Thread.currentThread().getName() + " calls doSave, context = " + content)
                .ifPresent(System.out::println);
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write(content);
        fileWriter.close();
    }
}
