package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.*;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
import Model.gameandbattle.map.Map;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import static Model.gameandbattle.map.Texture.RIVER;
import static Model.gameandbattle.map.Texture.SHALLOW_WATER;

public class GameMenuController {

    private final Map currentMap;
    private final ArrayList<Government> governments;
    private int turnCounter;
    private final int amountOfAllPlayers;

    public GameMenuController(ArrayList<Government> governments, Map currentMap) {
        turnCounter = 0;
        this.governments = governments;
        this.currentMap = currentMap;
        amountOfAllPlayers = governments.size();
        DataBank.initializeBuildingName();
        DataBank.initializeAllUnits();
        DataBank.initializeWalls();
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    private int removeDeadGovernments() {
        int counterLosers = 0;
        for (int i = governments.size() - 1; i >= 0; i--) {
            if (governments.get(i).getKing().getHp() <= 0) {
                governments.get(i).setAlive(false);
                counterLosers++;
                if (governments.get(i).getRuler().getHighScore() < turnCounter)
                    governments.get(i).getRuler().setHighScore(turnCounter);
                governments.remove(i);
            }
        }
        return counterLosers;
    }

    public String nextTurn() {
        turnCounter++;
        int counterLosers = removeDeadGovernments();
        if (counterLosers == amountOfAllPlayers - 1) {
            return "Game Over";
        }
        while (true) {
            if (!governments.get(turnCounter % amountOfAllPlayers).isAlive()) turnCounter++;
            else break;
        }
        DataBank.setCurrentGovernment(governments.get(turnCounter % amountOfAllPlayers));
        for (int i = 0; i < currentMap.getSize(); i++) {
            for (int j = 0; j < currentMap.getSize(); j++) {
                tunnelAbility(currentMap.getACell(i, j));
                affectElementsOfGame(i, j);
               /* for (int m = currentMap.getACell(i, j).getPeople().size() - 1; m >= 0; m--) {
                    Person person = currentMap.getACell(i, j).getPeople().get(m);
                    ArrayList<String> airAttackers = new ArrayList<>(List.of("Crossbowmen", "Archer Bow", "Horse Archers",
                            "Slingers", "Archer", "Fire Throwers"));
                    boolean flagAir = false;
                    for (String string : airAttackers) {
                        if (person.getName().equals(string)) {
                            flagAir = true;
                            break;
                        }
                    }
                    if (person.getPatrol() != null) {
                        Patrol patrol = person.getPatrol();
                        int condition = patrol.getCondition();
                        int x, y, x1, y1;
                        if (condition == 0) {
                            x = patrol.getStartX();
                            y = patrol.getStartY();
                            x1 = patrol.getFinishX();
                            y1 = patrol.getFinishY();
                        } else {
                            x = patrol.getFinishX();
                            y = patrol.getFinishY();
                            x1 = patrol.getStartX();
                            y1 = patrol.getStartY();
                        }
                        ArrayList<Integer> pathX = new ArrayList<>();
                        ArrayList<Integer> pathY = new ArrayList<>();
                        boolean[][] help = new boolean[currentMap.getSize()][currentMap.getSize()];
                        prepareHelp(help);
                        currentMap.getACell(i, j).getPeople().remove(m);
                        UnitMenuController.aStarSearch(help, x, y, x1, y1, pathX, pathY, currentMap.getSize(), currentMap.getSize());
                        int speed = 100;
                        if ((person instanceof Troop)) speed = ((Troop) person).getSpeed();
                        x = pathX.get(Math.min(pathX.size() - 1, speed / 25));
                        y = pathY.get(Math.min(pathY.size() - 1, speed / 25));
                        if (x == pathX.get(pathX.size() - 1) && y == pathY.get(pathY.size() - 1))
                            patrol.setCondition(1 - patrol.getCondition());
                        currentMap.getACell(x, y).getPeople().add(person);
                    }
                    if (person instanceof Troop && ((Troop) person).getState() == 2 && flagAir) {
                        for (int k = Math.max(i - 5, 0); k < Math.min(i + 5, currentMap.getSize() - 1); k++) {
                            for (int q = Math.max(j - 5, 0); q < Math.min(j + 5, currentMap.getSize() - 1); q++) {
                                for (Person person1 : currentMap.getACell(k, q).getPeople()) {
                                    if (!person1.getGovernment().getRuler().getUsername().equals(person.getGovernment().getRuler().getUsername())) {
                                        person1.setHp(person1.getHp() - ((Troop) person).getAttackStrength());
                                    }
                                }
                                if (currentMap.getACell(k, q).getBuilding() != null &&
                                        !currentMap.getACell(k, q).getBuilding().getGovernment().getRuler().getUsername().equals(person.getGovernment().getRuler().getUsername())) {
                                    currentMap.getACell(k, q).getBuilding().setHitpoint(currentMap.getACell(k, q).getBuilding().getHitpoint() - ((Troop) person).getAttackStrength());
                                }
                            }
                        }
                    }
                    int x = 0;
                    int y = 0;
                    if (person instanceof Troop && ((Troop) person).getState() != 2) {
                        int amountOfMove;
                        if (((Troop) person).getState() == 1) amountOfMove = 5;
                        else amountOfMove = 10;
                        outer:
                        for (int k = Math.max(i - amountOfMove, 0); k < Math.min(i + amountOfMove, currentMap.getSize() - 1); k++) {
                            for (int q = Math.max(j - amountOfMove, 0); q < Math.min(j + amountOfMove, currentMap.getSize() - 1); q++) {
                                Person toBeAdded = null;
                                int counter1 = 0;
                                for (Person person1 : currentMap.getACell(k, q).getPeople()) {
                                    if (!person1.getGovernment().getRuler().getUsername().equals(person.getGovernment().getRuler().getUsername())) {
                                        ArrayList<Integer> pathX = new ArrayList<>();
                                        ArrayList<Integer> pathY = new ArrayList<>();
                                        boolean[][] help = new boolean[currentMap.getSize()][currentMap.getSize()];
                                        prepareHelp(help);
                                        UnitMenuController.aStarSearch(help, i, j, k, q, pathX, pathY, currentMap.getSize(), currentMap.getSize());
                                        if (pathX.size() != 0) {
                                            int speed = 100;
                                            speed = ((Troop) person).getSpeed();
                                            x = pathX.get(Math.min(pathX.size() - 1, speed / 25));
                                            y = pathY.get(Math.min(pathY.size() - 1, speed / 25));
                                            toBeAdded = person;
                                        }
                                    }
                                    counter1++;
                                }
                                if (toBeAdded != null) {
                                    currentMap.getACell(x, y).getPeople().add(toBeAdded);
                                    currentMap.getACell(i, j).getPeople().remove(toBeAdded);
                                    break outer;
                                }
                            }
                        }
                    }

                }*/
            }
        }
        affectGovernments();
        return "it's your turn " + DataBank.getCurrentGovernment().getRuler().getNickname();
    }

    private void tunnelAbility(Cell cell) {
        if (cell.getTunnel() == null) return;
        cell.getTunnel().setLength(cell.getTunnel().getLength() + 1);
        if (cell.getTunnel().getLength() == Tunnel.limitLength) {
            if (cell.getBuilding() != null) removeBuilding(cell);
            removePeople(cell, true);
            cell.setTunnel(null);
        }
    }

    private void affectGovernments() {
        for (Government government : governments) {
            double amount = 0;
            if (government.getTaxRate() > 0) amount = 0.6 + (government.getTaxRate() - 1) * 0.2;
            else if (government.getTaxRate() == 0) amount = 0;
            else amount = -1 * (0.6 + (Math.abs(government.getTaxRate()) - 1) * 0.2);
            government.setCoin(government.getCoin() + government.getPopulation() * amount);
            government.setPopularity(government.nonZeroFoods() - 1 + government.getPopularity());
            government.setPopularity(government.getPopularity() + 4 * government.getFoodRate());
            if (government.getTaxRate() <= 0)
                government.setPopularity(government.getPopularity() + (-2) * government.getTaxRate() + 1);
            else
                government.setPopularity(government.getPopularity() + (-2) * government.getTaxRate());
            government.setPopularity(government.getPopularity() + government.getFearRate());
            government.setPopularity(Math.min(100, government.getPopularity()));
            government.getGranary().setApple(government.getGranary().getApple() -
                    (1 + government.getFoodRate() * 0.5) * government.getPopulation());
            government.getGranary().setCheese(government.getGranary().getCheese() -
                    (1 + government.getFoodRate() * 0.5) * government.getPopulation());
            government.getGranary().setBread(government.getGranary().getBread() -
                    (1 + government.getFoodRate() * 0.5) * government.getPopulation());
            government.getGranary().setMeat(government.getGranary().getMeat() -
                    (1 + government.getFoodRate() * 0.5) * government.getPopulation());
            for (int i = 0; i < government.getFoods().length; i++) {
                government.getFoods()[i] = government.getFoods()[i] - (1 + government.getFoodRate() * 0.5) * government.getPopulation();
            }
            changePopulation(government);
        }
    }

    private void changePopulation(Government government) {
        int previousPopulation = government.getPopulation();
        government.setPopulation(government.getMaxPopulation() * government.getPopularity() / 100);
        for (int i = 0; i < government.getPopulation() - previousPopulation; i++) {
            government.addPerson();
        }
    }

    private void removeBuilding(Cell cell) {
        cell.getBuilding().getGovernment().getBuildings().remove(cell.getBuilding());
        cell.setBuilding(null);
        cell.setBuildingImage(null);
    }

    private void removePeople(Cell cell, boolean isTunnelDestroyed) {
        ArrayList<Person> people = new ArrayList<>();
        for (Person person : cell.getPeople()) {
            if (person.getHp() <= 0 || cell.getTexture().equals(Texture.PETROLEUM) || isTunnelDestroyed) {
                person.getBuilding().getWorkers().remove(person);
                person.getGovernment().getPeople().remove(person);
                people.add(person);
                person.getGovernment().setPopulation(person.getGovernment().getPopulation() - 1);
                if (cell.getQueues().getChildren().size() != 0) {
                    VBox left = (VBox) cell.getQueues().getChildren().get(0);
                    VBox right = (VBox) cell.getQueues().getChildren().get(1);
                    left.getChildren().remove(((Troop) person).getImageView());
                    right.getChildren().remove(((Troop) person).getImageView());
                }
            }
        }
        for (Person person : people) {
            cell.getPeople().remove(person);
        }
    }

    private void affectElementsOfGame(int i, int j) {
        removePeople(currentMap.getACell(i, j), false);
        if (currentMap.getACell(i, j).getBuilding() != null) {
            if (currentMap.getACell(i, j).getBuilding().getHitpoint() <= 0) {
                removeBuilding(currentMap.getACell(i, j));
            }
        }
        if (currentMap.getACell(i, j).getBuilding() != null) {
            currentMap.getACell(i, j).getBuilding().makeAffect(i, j, currentMap);
            if (currentMap.getACell(i, j).getBuilding().isFiery())
                removeBuilding(currentMap.getACell(i, j));
        }
        if (currentMap.getACell(i, j).isDitchUnderConstruction()) {
            currentMap.getACell(i, j).setTurnCounterForDitch(currentMap.getACell(i, j).getTurnCounterForDitch() + 1);
            if (currentMap.getACell(i, j).getTurnCounterForDitch() == 2)
                currentMap.getACell(i, j).setDitch();
        }
        if (currentMap.getACell(i, j).getTexture().equals(Texture.PETROLEUM))
            currentMap.getACell(i, j).setTexture(Texture.GROUND);
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    private static boolean findPathHelper(boolean[][] table, int currX, int currY, int endX, int endY,
                                          boolean[][] visited, ArrayList<Integer> pathX, ArrayList<Integer> pathY) {
        if (currX == endX && currY == endY) {
            pathX.add(currX);
            pathY.add(currY);
            return true;
        }
        if (currX >= 0 && currX < table.length && currY >= 0 && currY < table[0].length
                && !visited[currX][currY] && table[currX][currY]) {
            visited[currX][currY] = true;
            if (findPathHelper(table, currX + 1, currY, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX - 1, currY, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX, currY + 1, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX, currY - 1, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            visited[currX][currY] = false;
        }
        return false;
    }

    private static <T> void reverseList(ArrayList<T> list) {
        int size = list.size();
        for (int i = 0; i < size / 2; i++) {
            T temp = list.get(i);
            list.set(i, list.get(size - i - 1));
            list.set(size - i - 1, temp);
        }
    }

    public void prepareHelp(boolean[][] help) {
        for (int i = 0; i < currentMap.getSize(); i++) {
            for (int j = 0; j < currentMap.getSize(); j++) {
                if (currentMap.getACell(i, j).getTexture() == Texture.SEE || currentMap.getACell(i, j).getTexture() == SHALLOW_WATER ||
                        currentMap.getACell(i, j).getTexture() == Texture.ROCK || currentMap.getACell(i, j).getTexture() == RIVER ||
                        currentMap.getACell(i, j).getTexture() == Texture.SMALL_POUND ||
                        currentMap.getACell(i, j).getTexture() == Texture.LARGE_POUND) {
                    help[i][j] = false;
                } else {
                    help[i][j] = true;
                    if (currentMap.getACell(i, j).hasWall()) {
                        help[i][j] = currentMap.getACell(i, j).getWall().isAccessible();
                    }

                    if (currentMap.getACell(i, j).isDitch())
                        help[i][j] = false;
                }
            }
        }
    }
}
