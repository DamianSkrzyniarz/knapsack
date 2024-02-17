package pl.dskrzyniarz;

import java.util.*;

public class Chromosome implements Comparable<Chromosome>{
    private List<Item> itemList;
    private List<Boolean> itemInclusion;


    public int getTotalWeight(){
        int totalWeight = 0;
        for(int i=0; i<getLength(); i++){
            if(itemInclusion.get(i)){
                totalWeight += itemList.get(i).getWeight();
            }
        }
        return totalWeight;
    }
    public int getTotalValue(){
        int totalValue = 0;
        for(int i=0; i<getLength(); i++){
            if(itemInclusion.get(i)){
                totalValue += itemList.get(i).getValue();
            }
        }
        return totalValue;
    }
    public int getLength(){
        return itemList.size();
    }

    public Boolean getInclusion(int index){
        return itemInclusion.get(index);
    }

    public Chromosome(List<Item> items) {
        this.itemList = items;
        this.itemInclusion = new ArrayList<>();
        Random random = new Random();
        for(Item item: items){
            this.itemInclusion.add(random.nextBoolean());
        }
    }

    public Chromosome(List<Item> items, Chromosome firstParent, Chromosome secondParent){
        this.itemList = items;
        Random random = new Random();
        int split = random.nextInt(firstParent.getLength());
        itemInclusion = new ArrayList<>();
        for(int i=0; i< split; i++){ //copy values from first parent
            itemInclusion.add(firstParent.getInclusion(i));
        }
        for(int i=split; i< firstParent.getLength(); i++){ //copy values from second parent
            itemInclusion.add(secondParent.getInclusion(i));
        }

    }

    public void mutate(){
        Random random = new Random();
        int mutatingGene = random.nextInt(itemList.size());
        if(itemInclusion.get(mutatingGene)) {
            itemInclusion.set(mutatingGene, false);
        }
        else itemInclusion.set(mutatingGene, true);
    }

    @Override
    public int compareTo(Chromosome other) {
        return Integer.compare(this.getTotalValue(), other.getTotalValue());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{V:").append(this.getTotalValue()).append(", ");
        builder.append("W:").append(this.getTotalWeight()).append(" (");
        for(Boolean included: itemInclusion){
            if(included){
                builder.append("1");
            }
            else builder.append("0");
        }
        builder.append(")}");
        return builder.toString();
    }
}
