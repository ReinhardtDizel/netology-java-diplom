package ru.netology.graphics;

import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextColorSchemaImpl;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterImpl;
import ru.netology.graphics.server.GServer;

public class Main {

    private static final char[] schemaOne = {'#', '$', '@', '%', '*', '+', '-', '\''};
    private static final char[] schemaTwo = {'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};

    public static void main(String[] args) throws Exception {

        TextGraphicsConverter converter = new TextGraphicsConverterImpl();
        TextColorSchema schema = new TextColorSchemaImpl(schemaOne);
        converter.setTextColorSchema(schema);

        GServer server = new GServer(converter);
        server.start();

        String url = "https://u.netology.ngcdn.ru/backend/uploads/legacy/users/avatar/6395666/101906.jpeg";
        converter.setMaxRatio(2);
        String imgTxt = converter.convert(url);
        //System.out.println(imgTxt);
    }
}
