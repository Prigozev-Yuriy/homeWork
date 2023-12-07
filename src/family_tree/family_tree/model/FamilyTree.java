package family_tree.family_tree.model;

import family_tree.family_tree.model.human.Human;
import family_tree.family_tree.model.human.comparators.ComparatorById;
import family_tree.family_tree.model.human.comparators.ComparatorByName;
import family_tree.family_tree.model.human.comparators.ComporatorByAge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FamilyTree<G extends GeneralTypeTree<G>> implements Serializable, Iterable<G> {
    private long humansId;
    private List<G> humanList;
//    private String nameFamilyTree;
    public FamilyTree() {
        this(new ArrayList<>()); }
    public FamilyTree(List<G> humanList) {
        this.humanList = humanList; }
//    public FamilyTree(String nameFamilyTree, List<G> humanList) { // *** с двумя параметрами
//        this.nameFamilyTree = nameFamilyTree;
//        this.humanList = humanList;
//    }
    public boolean add(G human){   // метод добавления в семью нового человека
        if (human == null){    //проверка на наличие пустой ссылки
            return false;
        }
        if (!humanList.contains(human)){   //проверка что еще такого человека нет в нашей семье
            humanList.add(human);
            human.setId(humansId++);
            addToParents(human);    // методы создающие связи(обратные) с членами семьи
            addToChildren(human);

            return true;
        }
        return false;
    }
// метод позволяющий найти Human-a по идентификатору
    public G getById(long id){
        if (!checkId(id)){
            return null;
        }
        for (G human : humanList){
            if (human.getId() == id){
                return (G) human;
            }
        }
        return null;
    }
// метод поиска братьев и сестер
    public List<G> getSiblings(int id) { //пеередается идентификатор человека и возвр список детей
        G human = getById(id);
        if (human == null){
            return null;
        }
        List<G> res = new ArrayList<>();
        for (G parent: human.getParents()){
            for (G child: parent.getChildren()){
                if (!child.equals(human) && !res.contains(child)){

                }
            }
        }
        return res;
    }
// метод поиска по имени
    public List<G> getByName(String name){
        List<G> res = new ArrayList<>();
        for (G human : humanList){
            if (human.getName().equals(name)){
                res.add(human);
            }
        }
        return res;
    }
//метод создающий супружеские связи
    public boolean setWedding(long humanId1, long humanId2){
        if (checkId(humanId1) && checkId(humanId2)){
            G human1 = getById(humanId1); //работа с идентификаторами
            G human2 = getById(humanId2);
            return setWedding(human1, human2);
        }
        return false;
    }
    public boolean setWedding(G human1, G human2){
        if (human1.getSpouse() == null && human2.getSpouse() == null){
            human1.setSpouse(human2); // работа с именами
            human2.setSpouse(human1);
            return true;
        } else {
            return false;
        }
    }
// метод развода
    public boolean setDivorse(long eId1, long eId2){
        if (checkId(eId1) && checkId(eId2)) {
            G e1 = getById(eId1);
            G e2 = getById(eId2);
            return setDivorse(e1, e2);
        }
        return false;
    }
    public boolean setDivorse(G e1, G e2){
        if (e1.getSpouse() != null && e2.getSpouse() != null){
            e1.setSpouse(null);
            e2.setSpouse(null);
            return true;
        } else {
            return false;
        }
    }
//удаление из семейного дерева (случайно добавили, или по ошибке)
    public boolean remove(long eId){
        if (checkId(eId)){
            G human = getById(eId);
            return humanList.remove(human);
        }
        return false;
    }
// создание родственных связей в обе стороны
    private void addToParents(G human){
        for (G parent: human.getParents()){
            parent.addChild(human); // у родителей появился новый ребенок
        }
    }
    private void addToChildren(G human){
        for (G child : human.getChildren()){
            child.addParent(human); // ребенку сообщаем что у него есть родители
        }
    }
    // метод предварительной проверки идентификатора в нужном диапазоне
    public boolean checkId(long id){
        return id < humansId && id >= 0;
    }
// метод возвращающий информацию по всем дереву (перебор всех людей)
    public String getInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("В дереве ");
        sb.append(humanList.size());
        sb.append(" объектов: \n");
        for (G human: humanList){
            sb.append(human);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public Iterator<G> iterator() {
        return new HumanIterator(humanList);
    }
    public void getByName(){
        humanList.sort(new ComparatorByName());
    }
    public void getByAge(){
        humanList.sort(new ComporatorByAge());
    }

    public void addHuman(G human) {
        if (!humanList.contains(human)){
            human.setId(humansId++);
            humanList.add(human);
            addToParents(human);
            addToChildren(human);
        }
    }

    public void sortByName() {
        humanList.sort(new ComparatorByName<>());
    }

    public void sortByAge() {
        humanList.sort(new ComporatorByAge<>());
    }

    public void sortById() {
        humanList.sort(new ComparatorById<>());
    }
}
