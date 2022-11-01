package com.ilumer.textpastry;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

public class PasteLinesAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final CaretModel caretModel = editor.getCaretModel();
        Document document = editor.getDocument();

        String clipboard = "";
        Transferable currentItem = CopyPasteManager.getInstance().getContents();
        if (currentItem != null && currentItem.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                clipboard = currentItem.getTransferData(DataFlavor.stringFlavor).toString();
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }

        String[] wordList = clipboard.split("\n");
        WriteCommandAction.runWriteCommandAction(project, () -> {
            List<Caret> carets = caretModel.getAllCarets();
            for (int i = 0; i < carets.size(); i++) {
                Caret caret = carets.get(i);

                String appendText = "";
                if (i < wordList.length) {
                    appendText = wordList[i];
                }
                int end = caret.getSelectionEnd();
                document.insertString(end, appendText);
                caret.moveToOffset(end + appendText.length());
            }
        });
    }
}
