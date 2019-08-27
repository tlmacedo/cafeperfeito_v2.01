package br.com.tlmacedo.cafeperfeito.service.autoComplete;

import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

public class ServiceAutoCompleteComboBox<T> {

    private final Class<T> classe;
    private ComboBox<T> comboBox;
    private ObservableList<T> observableList;
    private FilteredList<T> filteredList;

    private boolean moveCaretToPos = false;
    private int caretPos;


    public ServiceAutoCompleteComboBox(Class<T> classe, ComboBox<T> comboBox) {
        this.classe = classe;
        setComboBox(comboBox);
        new ServiceMascara().fieldMask(getComboBox().getEditor(), "UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");

        setFilteredList(new FilteredList<>(getComboBox().getItems()));
        getComboBox().setItems(getFilteredList());

//        getComboBox().setEditable(true);
        getComboBox().focusedProperty().addListener((ov, o, n) -> {
            if (n) {
                getComboBox().setEditable(true);
                Platform.runLater(() -> getComboBox().getEditor().selectAll());
            } else {
                getComboBox().setEditable(false);
            }
        });
        getComboBox().setOnKeyPressed(t -> getComboBox().hide());
        getComboBox().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                return;
            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.RIGHT
                    || event.getCode() == KeyCode.LEFT || event.getCode().equals(KeyCode.SHIFT)
                    || event.getCode().equals(KeyCode.CONTROL) || event.isControlDown()
                    || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
                    || event.getCode() == KeyCode.TAB) {
                return;
            }
            if (event.getCode() == KeyCode.ENTER) {
                if (getComboBox().getSelectionModel().getSelectedItem() != null) {
                    getComboBox().setValue(getComboBox().getSelectionModel().getSelectedItem());
                    getComboBox().hide();
                    getComboBox().setEditable(false);
                }
                return;
            }

            switch (getClasse().getSimpleName().toLowerCase()) {
                case "empresa":
                    getFilteredList().setPredicate(flist -> {
                        if (((Empresa) flist).getRazao() == null)
                            return true;
                        else if (((Empresa) flist).getRazao().toLowerCase().contains(
                                ServiceAutoCompleteComboBox.this.
                                        getComboBox().getEditor().getText().toLowerCase()))
                            return true;
                        else if (((Empresa) flist).getFantasia().toLowerCase().contains(
                                ServiceAutoCompleteComboBox.this.
                                        getComboBox().getEditor().getText().toLowerCase()))
                            return true;
                        return false;
                    });
                    break;
                default:
                    getFilteredList().setPredicate(flist -> {
                        if (flist.toString().toLowerCase().contains(
                                ServiceAutoCompleteComboBox.this.
                                        getComboBox().getEditor().getText().toLowerCase()))
                            return true;
                        return false;
                    });
                    break;
            }
            if (!getFilteredList().isEmpty())
                getComboBox().show();

        });

        getComboBox().setConverter(new StringConverter<T>() {
            @Override
            public String toString(T object) {
                if (object != null)
                    return object.toString();
                return null;
            }

            @Override
            public T fromString(String string) {
                for (T obj : getFilteredList())
                    if (obj.toString().toLowerCase().contains(string.toLowerCase()))
                        return obj;
                return null;
            }
        });

//        getComboBox().showingProperty().addListener((ov, o, n) -> {
//            if (!n) {
//                if (getComboBox().getSelectionModel().getSelectedItem() != null) {
//                    getComboBox().setValue(getComboBox().getSelectionModel().getSelectedItem());
//                    getComboBox().setEditable(false);
//                }
//            }
//        });

//        getComboBox().setConverter(new StringConverter<T>() {
//            @Override
//            public String toString(T object) {
//                System.out.printf("object\n");
//                if (object == null) return null;
//                return object.toString();
//            }
//
//            @Override
//            public T fromString(String string) {
//                System.out.printf("string\n");
//                switch (getClasse().getSimpleName().toLowerCase()) {
//                    case "empresa":
//                        for (T obj : getComboBox().getItems())
//                            if (((Empresa) obj).toString().equals(string.toLowerCase())) {
//                                System.out.printf("string: [%s]\t\tobj: [%s]\n", string, obj);
//                                return obj;
//                            }
//                        break;
//                    default:
//                        for (T obj : getComboBox().getItems())
//                            if (obj.toString().toLowerCase().equals(string.toLowerCase()))
//                                return obj;
//                        break;
//                }
//                return null;
//            }
//        });
    }

//    @Override
//    public void handle(KeyEvent event) {
//        if (event.getCode() == KeyCode.UP) {
//            caretPos = -1;
//            moveCaret(getComboBox().getEditor().getText().length());
//            return;
//        } else if (event.getCode() == KeyCode.DOWN) {
//            if (!getComboBox().isShowing())
//                getComboBox().show();
//            caretPos = -1;
//            moveCaret(getComboBox().getEditor().getText().length());
//            return;
//        } else if (event.getCode() == KeyCode.BACK_SPACE) {
//            moveCaretToPos = true;
//            caretPos = getComboBox().getEditor().getCaretPosition();
//        } else if (event.getCode() == KeyCode.DELETE) {
//            moveCaretToPos = true;
//            caretPos = getComboBox().getEditor().getCaretPosition();
//        } else if (event.getCode() == KeyCode.ENTER) {
//            return;
//        }
//        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode().equals(KeyCode.SHIFT) || event.getCode().equals(KeyCode.CONTROL)
//                || event.isControlDown() || event.getCode() == KeyCode.HOME
//                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
//            return;
//        }
//
//
//        String t = getComboBox().getEditor().getText();
//
//        setFilteredList(new FilteredList<>(getComboBox().getItems()));
//        getComboBox().setItems(getFilteredList());
//
//        getComboBox().getEditor().setText(t);
//        if (!moveCaretToPos) {
//            caretPos = -1;
//        }
//        moveCaret(t.length());
//        if (!getFilteredList().isEmpty())
//            getComboBox().show();
//
//    }


//    @Override
//    public void handle(KeyEvent event) {
//        if (event.getCode() == KeyCode.ESCAPE) {
//            getComboBox().hide();
//            return;
//        }
//        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode().equals(KeyCode.SHIFT) || event.getCode().equals(KeyCode.CONTROL)
//                || event.isControlDown() || event.getCode() == KeyCode.HOME
//                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
//            return;
//        }
//
//        switch (getClasse().getSimpleName().toLowerCase()) {
//            case "empresa":
//                getFilteredList().setPredicate(flist -> {
//                    if (((Empresa) flist).getRazao().toLowerCase().contains(
//                            ServiceAutoCompleteComboBox.this.
//                                    getComboBox().getEditor().getText().toLowerCase()))
//                        return true;
//                    else if (((Empresa) flist).getFantasia().toLowerCase().contains(
//                            ServiceAutoCompleteComboBox.this.
//                                    getComboBox().getEditor().getText().toLowerCase()))
//                        return true;
//                    return false;
//                });
//                break;
//            default:
//                getFilteredList().setPredicate(flist -> {
//                    if (flist.toString().toLowerCase().contains(
//                            ServiceAutoCompleteComboBox.this.
//                                    getComboBox().getEditor().getText().toLowerCase()))
//                        return true;
//                    return false;
//                });
//                break;
//        }
//        if (!getFilteredList().isEmpty())
//            getComboBox().show();
//    }

    public Class<T> getClasse() {
        return classe;
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox<T> comboBox) {
        this.comboBox = comboBox;
    }

    public ObservableList<T> getObservableList() {
        return observableList;
    }

    public void setObservableList(ObservableList<T> observableList) {
        this.observableList = observableList;
    }

    public FilteredList<T> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(FilteredList<T> filteredList) {
        this.filteredList = filteredList;
    }

}
