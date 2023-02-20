package kakfka_demo.serializers.bytes;


import kakfka_demo.xlsx.schema.Document;
import org.apache.kafka.common.errors.SerializationException;

import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class DocumentDeserializer implements Deserializer<Document> {
    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Document deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null recieved at deserialize");
                return null;
            }
            ByteBuffer buffer = ByteBuffer.wrap(data);


//            int sizeOfDocumentName = buffer.getInt();
//            byte[] documentNameBytes = new byte[sizeOfDocumentName];
//            buffer.get(documentNameBytes);
//            String deserializedName = new String(documentNameBytes, encoding);

            int sizeOfDocumentOwner=buffer.getInt();
            byte[] documentOwnerBytes = new byte[sizeOfDocumentOwner];
            buffer.get(documentOwnerBytes);
            String deserializedOwner = new String(documentOwnerBytes, encoding);

            int sizeOfDocumentSource = buffer.getInt();
            byte[] documentSourceBytes = new byte[sizeOfDocumentSource];
            buffer.get(documentSourceBytes);
            String deserializedSource = new String(documentSourceBytes, encoding);

            int sizeOfDocumentResult = buffer.getInt();
            byte[] documentResultBytes = new byte[sizeOfDocumentResult];
            buffer.get(documentResultBytes);
            String deserializedResult = new String(documentResultBytes, encoding);


            return new Document(
                    //deserializedName,
                    deserializedOwner,
                    deserializedSource,
                    deserializedResult);

        } catch (Exception e) {
            throw new SerializationException("Error when deserialize byte[] to Document");
        }
    }


    @Override
    public void close() {

    }
}
