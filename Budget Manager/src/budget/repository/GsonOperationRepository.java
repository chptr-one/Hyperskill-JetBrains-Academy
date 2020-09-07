package budget.repository;

import budget.domain.Income;
import budget.domain.Operation;
import budget.domain.Purchase;
import budget.domain.PurchaseType;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonOperationRepository implements OperationRepository {

    private static OperationRepository instance;
    private List<Operation> operations;
    private final File file;

    public static OperationRepository getInstance() {
        if (instance == null) {
            instance = new GsonOperationRepository();
        }
        return instance;
    }

    private GsonOperationRepository() {
        this.operations = new ArrayList<>();
        file = new File("purchases.txt");
    }

    @Override
    public void save() {
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Operation.class, new OperationAdapter())
                    .create();
            gson.toJson(operations, new TypeToken<ArrayList<Operation>>() {
            }.getType(), writer);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Purchases not saved due to IO error!");
        }
    }

    @Override
    public void load() {
        try (Reader reader = new FileReader(file)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Operation.class, new OperationAdapter())
                    .create();
            Type listType = new TypeToken<ArrayList<Operation>>() {
            }.getType();
            operations = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Purchases not loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Operation operation) {
        operations.add(operation);
    }

    @Override
    public List<Purchase> getPurchases() {
        return operations.stream()
                .filter(operation -> operation instanceof Purchase)
                .map(operation -> (Purchase) operation)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> getPurchasesByType(PurchaseType type) {
        return operations.stream()
                .filter(operation -> operation instanceof Purchase)
                .map(operation -> (Purchase) operation)
                .filter(purchase -> purchase.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<Income> getIncomes() {
        return operations.stream()
                .filter(operation -> operation instanceof Income)
                .map(operation -> (Income) operation)
                .collect(Collectors.toList());
    }

    private static class OperationAdapter implements JsonSerializer<Operation>, JsonDeserializer<Operation> {

        private static final String CLASS_NAME = "CLASS_NAME";
        private static final String INSTANCE = "INSTANCE";

        @Override
        public JsonElement serialize(Operation src, Type typeOfSrc,
                                     JsonSerializationContext context) {

            JsonObject retValue = new JsonObject();
            String className = src.getClass().getName();
            retValue.addProperty(CLASS_NAME, className);
            JsonElement elem = context.serialize(src);
            retValue.add(INSTANCE, elem);
            return retValue;
        }

        @Override
        public Operation deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASS_NAME);
            String className = prim.getAsString();

            Class<?> klass;
            try {
                klass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new JsonParseException(e.getMessage());
            }
            return context.deserialize(jsonObject.get(INSTANCE), klass);
        }
    }
}
