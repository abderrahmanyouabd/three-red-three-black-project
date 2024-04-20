module com.a1st.threeredthreeblack {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;


    opens com.a1st.threeredthreeblack to javafx.fxml;
    exports com.a1st.threeredthreeblack;
}