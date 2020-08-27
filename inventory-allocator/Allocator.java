import java.util.*;

public class Allocator {

    // given a map of orders and a list of warehouses, produces a map of best orders
    // from each warehouse
    public static HashMap<String, HashMap<String, Integer>> bestWay(HashMap<String, Integer> order,
            ArrayList<Warehouse> warehouses) {
        HashMap<String, HashMap<String, Integer>> result = new HashMap<>();

        for (Map.Entry<String, Integer> currEntry : order.entrySet()) {
            int itemsLeft = currEntry.getValue();
            String currItem = currEntry.getKey();
            for (Warehouse warehouse : warehouses) {
                if (itemsLeft == 0) {
                    break;
                } else if (warehouse.getInventory().containsKey(currItem)) {
                    int warehouseItems = warehouse.getInventory().get(currItem);
                    HashMap<String, Integer> currWarehouse = result.getOrDefault(warehouse.getName(), new HashMap<>());
                    if (itemsLeft >= warehouseItems) {
                        currWarehouse.put(currItem, warehouseItems);
                        itemsLeft -= warehouseItems;
                    } else {
                        currWarehouse.put(currItem, itemsLeft);
                        itemsLeft = 0;
                    }
                    result.put(warehouse.getName(), currWarehouse);
                }
            }
            if (itemsLeft != 0) {
                return new HashMap<>();
            }
        }

        return result;
    }

    // given the map of best orders from each warehouse, prints the result as a list
    public static void printResult(HashMap<String, HashMap<String, Integer>> finalResult,
            ArrayList<Warehouse> warehouses) {
        System.out.print("[ ");
        // for (HashMap<String, HashMap<String, Integer>> currWarehouse : finalResult) {
        for (int i = 0; i < warehouses.size(); i++) {
            String currWarehouseName = warehouses.get(i).getName();
            if (finalResult.containsKey(currWarehouseName)) {
                HashMap<String, Integer> currWarehouse = finalResult.get(currWarehouseName);
                System.out.print("{ ");
                System.out.print("name: " + currWarehouseName + ", ");
                System.out.print("inventory: { ");
                for (Map.Entry<String, Integer> currWareHouseEntry : currWarehouse.entrySet()) {
                    System.out.print(currWareHouseEntry.getKey() + ": " + currWareHouseEntry.getValue() + ", ");

                }
                System.out.print("} ");
                System.out.print("}, ");
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        // BEGIN TEST 1:
        /*
         * { apple: 5, banana: 5, orange: 5 } [ { name: owd, inventory: { apple: 5,
         * orange: 10 } }, { name: dm:, inventory: { banana: 5, orange: 10 } } ]
         */
        HashMap<String, Integer> order = new HashMap<>();
        ArrayList<Warehouse> warehouses = new ArrayList<>();

        order.put("apple", 5);
        order.put("banana", 5);
        order.put("orange", 5);

        HashMap<String, Integer> owdInventory = new HashMap<>();
        owdInventory.put("apple", 5);
        owdInventory.put("orange", 10);
        Warehouse owd = new Warehouse("owd", owdInventory);

        HashMap<String, Integer> dmInventory = new HashMap<>();
        dmInventory.put("banana", 5);
        dmInventory.put("orange", 10);
        Warehouse dm = new Warehouse("dm", dmInventory);

        warehouses.add(owd);
        warehouses.add(dm);

        HashMap<String, HashMap<String, Integer>> warehouseResult = bestWay(order, warehouses);
        printResult(warehouseResult, warehouses);
        // END TEST 1:

        // BEGIN TEST 2:
        /*
         * { apple: 1} [ { name: owd, inventory: { apple: 1,} } ]
         */
        HashMap<String, Integer> order2 = new HashMap<>();
        ArrayList<Warehouse> warehouses2 = new ArrayList<>();

        order2.put("apple", 1);

        HashMap<String, Integer> owdInventory2 = new HashMap<>();
        owdInventory2.put("apple", 1);
        Warehouse owd2 = new Warehouse("owd", owdInventory2);

        warehouses2.add(owd2);

        HashMap<String, HashMap<String, Integer>> warehouseResult2 = bestWay(order2, warehouses2);
        printResult(warehouseResult2, warehouses2);
        // END TEST 2:

        // BEGIN TEST 3:
        /*
         * { apple: 10 }, [{ name: owd, inventory: { apple: 5 } }, { name: dm,
         * inventory: { apple: 5 }}]
         */
        HashMap<String, Integer> order3 = new HashMap<>();
        ArrayList<Warehouse> warehouses3 = new ArrayList<>();

        order3.put("apple", 10);

        HashMap<String, Integer> owdInventory3 = new HashMap<>();
        owdInventory3.put("apple", 5);
        Warehouse owd3 = new Warehouse("owd", owdInventory3);

        HashMap<String, Integer> dmInventory3 = new HashMap<>();
        dmInventory3.put("apple", 5);
        Warehouse dm3 = new Warehouse("dm", dmInventory3);

        warehouses3.add(owd3);
        warehouses3.add(dm3);

        HashMap<String, HashMap<String, Integer>> warehouseResult3 = bestWay(order3, warehouses3);
        printResult(warehouseResult3, warehouses3);
        // END TEST 3:

        // BEGIN TEST 4:
        /*
         * { apple: 1 }, [{ name: owd, inventory: { apple: 0 } }]
         */
        HashMap<String, Integer> order4 = new HashMap<>();
        ArrayList<Warehouse> warehouses4 = new ArrayList<>();

        order3.put("apple", 1);

        HashMap<String, Integer> owdInventory4 = new HashMap<>();
        owdInventory4.put("apple", 0);
        Warehouse owd4 = new Warehouse("owd", owdInventory4);

        warehouses4.add(owd4);

        HashMap<String, HashMap<String, Integer>> warehouseResult4 = bestWay(order4, warehouses4);
        printResult(warehouseResult4, warehouses4);
        // END TEST 4

    }
}

class Warehouse {
    String name;
    HashMap<String, Integer> inventory;

    public Warehouse(String name, HashMap<String, Integer> inventory) {
        this.name = name;
        this.inventory = inventory;
    }

    String getName() {
        return name;
    }

    HashMap<String, Integer> getInventory() {
        return inventory;
    }
}