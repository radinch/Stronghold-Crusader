package Controller;

import Model.gameandbattle.Government;

import java.util.regex.Matcher;

public class GovernmentMenuController {
    public String showPopularityFactors(Government government) {
        String output = "";
        if (government.getTaxRate() != 0) {
            output += "Tax\\n";
        }
        if (government.getFoodRate() != 0) {
            output += "Food\\n";
        }
        if (government.getFearRate() != 0) {
            output += "Fear\\n";
        }
        if (government.getBuildingByName("Church") != null || government.getBuildingByName("Cathedral") != null) {
            output += "Religion\\n";
        }
        return output;
    }

    public int showPopularity(Government government) {
        return government.getPopularity();
    }

    public void changeFoodRate(Matcher matcher, Government government) {
        int rate = Integer.parseInt(matcher.group("rate"));
        government.setFoodRate(rate);
        government.setPopularity(government.getPopularity() + 4 * rate);
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

    public void changeTaxRate(Government government, Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rate"));
        government.setTaxRate(rateNumber);
        if (rateNumber <= 0)
            government.setPopularity(government.getPopularity() + -2 * rateNumber + 1);
        else
            government.setPopularity(government.getPopularity() + -2 * rateNumber);
    }

    public int showTaxRate(Government government) {
        return government.getTaxRate();
    }

    public void changeFearRate(Government government, Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rate"));
        government.setFearRate(rateNumber);
        government.setPopularity(government.getPopularity() + rateNumber);
        government.setWorkersEfficiency(100 + 5 * rateNumber);
    }
}
