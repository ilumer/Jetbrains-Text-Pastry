package com.ilumer.textpastry;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public abstract class TextPastryBaseAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Get access to the editor and caret model. update() validated editor's existence.
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final CaretModel caretModel = editor.getCaretModel();
        Document document = editor.getDocument();
        // Getting the primary caret ensures we get the correct one of a possible many.
        for (int i = 0; i < caretModel.getAllCarets().size(); i++) {
            Caret caret = caretModel.getAllCarets().get(i);
            int end = caret.getSelectionEnd();
            // Replace the selection with a fixed string.
            // Must do this document change in a write action context.
            String appendText = generateAppendStr(i);
            WriteCommandAction.runWriteCommandAction(project, () -> document.insertString(end, appendText));
            caret.moveToOffset(end + appendText.length());
        }
    }

    public abstract String generateAppendStr(int index);
}
