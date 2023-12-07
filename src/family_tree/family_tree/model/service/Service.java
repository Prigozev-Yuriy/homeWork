package family_tree.family_tree.model.service;

import family_tree.family_tree.model.*;
import family_tree.family_tree.model.human.Human;

import java.time.LocalDate;

public class Service {
    private int index;
    private FamilyTree newTree;
    private Writable<FamilyTree> writable;

    public Service() {
        newTree = new FamilyTree<>();
        this.writable = writable;

    }
public boolean save(String filePath){
        if (writable == null){
            return false;
        }
        return writable.save(newTree, filePath);
}
public boolean read(String filePath){
        FamilyTree tempTree = writable.read(filePath);
//        tempTree = writable.read(filePath);
        if (writable == null){
            return false;
        }
        newTree = tempTree;
        return true;
}


    public String getHumanListInfo() {
        return newTree.getInfo();

}

    public String addHuman(String name,  String genderString, String birthDate){
        Gender gender = Gender.valueOf(genderString);
        LocalDate humanBirthDare = LocalDate.parse(birthDate);
        Human human = new Human(name, gender, humanBirthDare);
        newTree.addHuman(human);
        return "Человек добавлен";
    }
    public void sortByName() {
        newTree.sortByName();
    }
    public void sortByAge() {
        newTree.sortByAge();
    }
    public void sortById() {
        newTree.sortById();
    }

    public void setParent(int person_id, int parent_id){
        Human human = (Human) newTree.getById(person_id);
        human.addParent(newTree.getById(parent_id));
        newTree.getById(parent_id).addChild(human);
    }

    public boolean checkId(int id){
        return newTree.checkId(id);
    }


}
