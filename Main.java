
public class Main {
    public static MemorySpace MEMORY = new MemorySpace();

    public static void main(String[] args) throws Exception {

        String response = "test.txt";
        TokenStream inStream = new TokenStream();
        if (!response.equals("")) {
            inStream = new TokenStream(response);
        }
        while (response.equals("") || inStream.hasNext()) {
            System.out.print(">>> ");
            Statement stmt = Statement.getStatement(inStream);
            if (!response.equals(""))
                System.out.println(stmt);
            try {
                stmt.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
