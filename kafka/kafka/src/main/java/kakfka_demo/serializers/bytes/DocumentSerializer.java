package kakfka_demo.serializers.bytes;
import kakfka_demo.xlsx.schema.Document;
import org.apache.kafka.common.serialization.Serializer;
import java.nio.ByteBuffer;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
public class DocumentSerializer  implements Serializer<Document>{
    private String encoding = "UTF8";
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Document document) {

        //int sizeOfDocumentName;
        int sizeOfDocumentOwner;
        int sizeOfDocumentSource;
        int sizeOfDocumentResult;


        //byte[] serializedDocumentName;
        byte[] serializedDocumentSource;
        byte[] serializedDocumentResult;
        byte[] serializedDocumentOwner;

        try {
            if (document == null)
                return null;
//            serializedDocumentName = document.getName().getBytes(encoding);
//            sizeOfDocumentName = serializedDocumentName.length;

            serializedDocumentOwner = document.getOwner().getBytes(encoding);
            sizeOfDocumentOwner = serializedDocumentOwner.length;

            serializedDocumentSource = document.getSourceLink().getBytes(encoding);
            sizeOfDocumentSource = serializedDocumentSource.length;

            serializedDocumentResult = document.getResultLink().getBytes(encoding);
            sizeOfDocumentResult = serializedDocumentSource.length;


            ByteBuffer buffer = ByteBuffer.allocate(
                    //4+sizeOfDocumentName +
                            4+sizeOfDocumentSource +
                            4+sizeOfDocumentResult +
                            4+sizeOfDocumentOwner );


//            buffer.putInt(sizeOfDocumentName);
//            buffer.put(serializedDocumentName);
            buffer.putInt(sizeOfDocumentOwner);
            buffer.put(serializedDocumentOwner);
            buffer.putInt(sizeOfDocumentSource);
            buffer.put(serializedDocumentSource);
            buffer.putInt(sizeOfDocumentResult);
            buffer.put(serializedDocumentResult);

            return buffer.array();

        } catch (Exception e) {
            throw new SerializationException("Error when serializing Document  to byte[]");
        }
    }

    @Override
    public void close() {

    }
}
