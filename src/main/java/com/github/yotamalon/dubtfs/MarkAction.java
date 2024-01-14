package com.github.yotamalon.dubtfs;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MarkAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent event) {

    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file == null) {
            return;
        }
        String file_name = file.getName();
        CaretModel caret = editor.getCaretModel();
        int line = caret.getPrimaryCaret().getVisualPosition().line + 1;
        this.addDubTF(project, file_name, line);
    }

    private void addDubTF(Project project, String file_name, int line){
        try {
            BufferedWriter dubtfs_file = new BufferedWriter(new FileWriter(project.getBasePath() + "/.dubtfs", true));
            dubtfs_file.write(String.format("[f=%s,l=%s]", file_name, line));
            dubtfs_file.flush();
        } catch (IOException ignored) {
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
