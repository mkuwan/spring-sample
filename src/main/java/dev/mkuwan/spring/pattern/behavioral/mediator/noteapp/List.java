package dev.mkuwan.spring.pattern.behavioral.mediator.noteapp;

import javax.swing.*;

@SuppressWarnings("unchecked")
public class List extends JList implements Component {
    private Mediator mediator;
    private final DefaultListModel defaultListModel;

    public List(DefaultListModel defaultListModel) {
        super(defaultListModel);
        this.defaultListModel = defaultListModel;
        setModel(defaultListModel);
        this.setLayoutOrientation(JList.VERTICAL);
        Thread thread = new Thread(new Hide(this));
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void addElement(Note note){
        defaultListModel.addElement(note);
        int index = defaultListModel.size() - 1;
        setSelectedIndex(index);
        ensureIndexIsVisible(index);
        mediator.sendToFilter(defaultListModel);
    }

    public void deleteElement(){
        int index = this.getSelectedIndex();
        try{
            defaultListModel.remove(index);
            mediator.sendToFilter(defaultListModel);
        } catch (ArrayIndexOutOfBoundsException ex){

        }
    }

    public Note getCurrentElement(){
        return (Note)getSelectedValue();
    }

    @Override
    public String getName() {
        return "List";
    }

    private class Hide implements Runnable {
        private List list;

        Hide(List list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (list.isSelectionEmpty()) {
                    mediator.hideElements(true);
                } else {
                    mediator.hideElements(false);
                }
            }
        }
    }
}
