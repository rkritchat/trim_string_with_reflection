package model;

import anotation.Required;

public class ProductModel {
    @Required
    private NoteBookModel noteBookModel;

    public NoteBookModel getNoteBookModel() {
        return noteBookModel;
    }

    public void setNoteBookModel(NoteBookModel noteBookModel) {
        this.noteBookModel = noteBookModel;
    }
}
