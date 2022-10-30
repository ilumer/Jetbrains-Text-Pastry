package com.ilumer.textpastry;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class TextPastryBaseAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Get access to the editor and caret model. update() validated editor's existence.
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final CaretModel caretModel = editor.getCaretModel();
        Document document = editor.getDocument();
        WriteCommandAction.runWriteCommandAction(project, () -> {
            List<Caret> carets = caretModel.getAllCarets();
            for (int i = 0; i < carets.size(); i++) {
                Caret caret = carets.get(i);
                // Replace the selection with a fixed string.
                // Must do this document change in a write action context.
                String appendText = generateAppendStr(i);
                int end = caret.getSelectionEnd();
                document.insertString(end, appendText);
                caret.moveToOffset(end + appendText.length());
            }
        });
    }


    public abstract String generateAppendStr(int index);
}
