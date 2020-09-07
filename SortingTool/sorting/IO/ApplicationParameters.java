package sorting.IO;

public class ApplicationParameters {
    private DataType dataType;
    private SortingType sortingType;

    private ApplicationParameters(DataType dataType, SortingType sortingType) {
        this.dataType = dataType;
        this.sortingType = sortingType;
    }

    public static ApplicationParameters parseFrom(String[] args) throws ArgumentsExeption {
        if (args.length == 0)
            throw new ArgumentsExeption("No commands found");

        DataType dataType = null;
        SortingType sortingType = SortingType.NATURAL;

        int i = 0;
        while (i < args.length) {
            String commandName = args[i++];
            ParamType command = ParamType.fromString(commandName);
            if (command == null)
                throw new ArgumentsExeption("Wrong command " + commandName);

            if (i >= args.length)
                throw new ArgumentsExeption("No parameter for command " + commandName);
            String value = args[i++];

            switch (command) {
                case DATA_TYPE: {
                    dataType = DataType.fromString(value);
                    if (dataType == null)
                        throw new ArgumentsExeption("Wrong parameter for command " + commandName);
                    break;
                }
                case SORTING_TYPE: {
                    sortingType = SortingType.fromString(value);
                    if (sortingType == null)
                        throw new ArgumentsExeption("Wrong parameter for command " + commandName);
                    break;
                }
            }
        }

        return new ApplicationParameters(dataType, sortingType);
    }

    public DataType getDataType() {
        return dataType;
    }

    public SortingType getSortingType() {
        return sortingType;
    }

    public static void main(String[] args) throws ArgumentsExeption {
        String[] a = {"-dataType", "long", "-sortingType", "byCount"};
        ApplicationParameters parameters = ApplicationParameters.parseFrom(a);
        System.out.println(parameters.getDataType());
        System.out.println(parameters.getSortingType());
    }
}
