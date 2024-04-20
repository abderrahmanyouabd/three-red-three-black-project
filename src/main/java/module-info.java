module three.red.three.black.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;


    opens com.a1st.threeredthreeblack.model to javafx.base;
    exports com.a1st.threeredthreeblack;
    opens com.a1st.threeredthreeblack to javafx.fxml;
}