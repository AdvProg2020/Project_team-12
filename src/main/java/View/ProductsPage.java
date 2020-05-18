package View;

import Controller.CommandProcessors.CommandProcessor;
import Controller.CommandProcessors.ProductPageCP;
import Controller.CommandProcessors.ProductsPageCP;
import Controller.CommandProcessors.PurchasePageCP;
import View.Exceptions.CustomerExceptions;
import View.Exceptions.InvalidCommandException;
import View.Profiles.RegisterPanel;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsPage extends Menu {
    static ProductsPageCP commandProcessor ;

    public ProductsPage(Menu parentMenu) {
        super("Product Page", parentMenu);
        submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getFilteringMenu());
        submenus.put(2, getSortingMenu());
        submenus.put(3, new RegisterPanel(this));
        setCommands();
    }
    public static void setCommandProcessor(ProductsPageCP cp){
        commandProcessor = cp;

    }
    protected Menu getGrandFatherMenu() {
        return this.parentMenu;
    }

    private void setCommands() {
        commands.add("view categories");
        commands.add("filtering");
        commands.add("sorting");
        commands.add("show products");
        commands.add("show product PR_(\\S+)$");
        commands.add("back");
        commands.add("help");
        commands.add("go to register panel");
    }

    protected Menu getFilteringMenu() {
        return new Menu("filtering", this) {
            public void setCommands() {
                commands.add("show available filters");
                commands.add("filter (\\w+)$");
                commands.add("current filters");
                commands.add("disable filter (\\w+)$");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. show available filters\n2. filter [an available filter]\n3. current filters\n4. disable filter [ a selected filter] \n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.equals(commands.get(0))) {
                    for (int i = 1; i <= commandProcessor.getAvailableFilters().size(); i++)
                        System.out.println(i + ". " + commandProcessor.getAvailableFilters().get(i - 1));
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    if (!commandProcessor.canFilter(commandDetails[2]))
                        throw new CustomerExceptions("can't use this filter");
                    if (commandProcessor.checkRangeFilters(commandDetails[2])) {
                        String minimumValue = getField("minimum value", "(\\d+)\\.(\\d+)$");
                        String maximumValue = getField("maximum value", "(\\d+)\\.(\\d+)$");
                        commandProcessor.filterByRange(commandDetails[2], Double.parseDouble(minimumValue), Double.parseDouble(maximumValue));
                    } else {
                        ArrayList<String> filterValues = new ArrayList<String>();
                        getSelectedOptions(filterValues);
                        commandProcessor.filterBySelectedFeatures(commandDetails[2], filterValues);
                    }
                    return this;
                } else if (command.equals(commands.get(2))) {
                    for (int i = 1; i <= commandProcessor.getCurrentFilters().size(); i++)
                        System.out.println(i + ". " + commandProcessor.getCurrentFilters().get(i - 1));
                    return this;
                } else if (command.matches(commands.get(3))) {
                    String[] commandDetails = command.split("\\s");
                    if (!commandProcessor.canDisableFilter(commandDetails[2]))
                        throw new CustomerExceptions("can't disable this filter");
                    commandProcessor.disableFilter(commandDetails[2]);
                    return this;
                } else if (command.equals(commands.get(4))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(5))) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }

            public ArrayList<String> getSelectedOptions(ArrayList<String> filterValues) throws Exception{
                System.out.println("add specification to filter (at least one)");
                String value = getField("value", "\\S+");
                filterValues.add(value);
                System.out.println("type <back> to continue or <next> to add more specifications");
                String command = getField("<next> or <back>", "(next|back)$");
                if (command.equals("next"))
                    getSelectedOptions(filterValues);
                return filterValues;
            }
        };
    }

    protected Menu getSortingMenu() {
        return new Menu("sorting", this) {
            public void setCommands() {
                commands.add("show available sorts");
                commands.add("sort (\\w+)$");
                commands.add("current sort");
                commands.add("disable sort");
                commands.add("back");
                commands.add("help");
            }

            @Override
            public void show() {
                if (commands.size() == 0) setCommands();
                System.out.println(this.getName() + "\n");
                showCommands();
            }

            public void showCommands() {
                System.out.println("commands\n1. show available sorts\n2. sort [an available sort]\n3. current sort\n4. disable sort\n4. back\n5. help");
            }

            @Override
            public Menu getCommand() throws Exception {
                System.out.println("what do you want to do?\n");
                String command = scanner.nextLine();
                if (command.equals(commands.get(0))) {
                    for (int i = 1; i <= commandProcessor.getAvailableSorts().size(); i++)
                        System.out.println(i + ". " + commandProcessor.getAvailableSorts().get(i - 1));
                    return this;
                } else if (command.matches(commands.get(1))) {
                    String[] commandDetails = command.split("\\s");
                    if (commandProcessor.canSort(commandDetails[2]))
                        throw new CustomerExceptions("can't use this sort");
                    commandProcessor.setSortType(commandDetails[2]);
                    return this;
                } else if (command.equals(commands.get(2))) {
                    System.out.println(commandProcessor.getCurrentSort());
                    return this;
                } else if (command.equals(commands.get(3))) {
                    commandProcessor.disableSort();
                    return this;
                } else if (command.equals(commands.get(4))) {
                    return getGrandFatherMenu();
                } else if (command.equals(commands.get(5))) {
                    showCommands();
                    return this;
                }
                throw new InvalidCommandException("invalid command");
            }
        };
    }

    @Override
    public void show() {
        System.out.println(this.getName() + "\ncommands\n");
        for (int i = 1; i <= commands.size(); i++) {
            if (i == 5)
                System.out.println("5. show product [productId]");
            else
                System.out.println(i + ". " + commands.get(i - 1));
        }
    }

    @Override
    public Menu getCommand() throws Exception {
        String command = scanner.nextLine();
        if (command.equals(commands.get(0))|| command.equals("1")) {
            for (int i = 1; i <= commandProcessor.getAllCategories().size(); i++)
                System.out.println(i + ". " + commandProcessor.getAllCategories().get(i - 1));
            return this;
        } else if (command.equals(commands.get(1))|| command.equals("2")) {
            return submenus.get(1);
        } else if (command.equals(commands.get(2))|| command.equals("3")) {
            return submenus.get(2);
        } else if (command.equals(commands.get(3))|| command.equals("4")) {
            for (int i = 1; i <= commandProcessor.getProducts().size(); i++)
                System.out.println(i + ". " + commandProcessor.getProducts().get(i - 1));
            return this;
        } else if (command.matches(commands.get(4))) {
            String[] commandDetails = command.split("\\s");
            if (!commandProcessor.doesProductExist(commandDetails[2]))
                throw new CustomerExceptions("product with this id doesn't exist");
            commandProcessor.goToProduct(commandDetails[2]);
            return new ProductPage(this, commandDetails[2]);
        } else if (command.equals(commands.get(5))|| command.equals("6")) {
            CommandProcessor.back();
            return this.parentMenu;
        } else if (command.equals(commands.get(6)) || command.equals("7")) {
            return this;
        } else if (command.equals(commands.get(7)) || command.equals("8")) {
            return submenus.get(3);
        }
        throw new InvalidCommandException("invalid command");
    }
}
