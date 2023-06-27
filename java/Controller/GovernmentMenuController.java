package Controller;

import Model.gameandbattle.Government;

import java.util.regex.Matcher;

public class GovernmentMenuController {
    public String showPopularityFactors(Government government) {
        String output = "";
        if (government.getTaxRate() != 0) {
            output += "Tax\n";
        }
        if (government.getFoodRate() != 0) {
            output += "Food\n";
        }
        if (government.getFearRate() != 0) {
            output += "Fear\n";
        }
        if (government.getBuildingByName("Church") != null || government.getBuildingByName("Cathedral") != null) {
            output += "Religion\n";
        }
        return output;
    }

    public int showPopularity(Government government) {
        return government.getPopularity();
    }

    public static void changeFoodRate(int rate,Government government) {
        government.setFoodRate(rate);
        if(government.getGranary().getTotalFood() <= 0)
            government.setFoodRate(-2);
    }

    public String showFoodList(Government government) {
        String result = "";
        result += "meat: " + government.getGranary().getMeat() + "\n";
        result += "cheese: " + government.getGranary().getCheese() + "\n";
        result += "apple: " + government.getGranary().getApple() + "\n";
        result += "bread: " + government.getGranary().getBread() + "\n";
        return result;
    }

    public int showFoodRate(Government government) {
        return government.getFoodRate();
    }

    public static void changeTaxRate(int rateNumber,Government government) {
        government.setTaxRate(rateNumber);
        if(government.getCoin() <= 0)
            government.setTaxRate(0);
    }

    public int showTaxRate(Government government) {
        return government.getTaxRate();
    }

    public static void changeFearRate(int rateNumber,Government government) {
        government.setFearRate(rateNumber);
        //government.setPopularity(government.getPopularity() + rateNumber);
        government.setWorkersEfficiency(100 + 5 * rateNumber);
    }
}
