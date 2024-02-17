package pl.dskrzyniarz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("./src/main/resources/data");
        try {
            Scanner scanner = new Scanner(file);
            int maxWeight = scanner.nextInt();
            List<Item> items = new ArrayList<>();
            while(scanner.hasNext()){
                items.add(new Item(scanner.nextInt(), scanner.nextInt()));
            }
            List<Chromosome> chromosomes = new ArrayList<>();
            while(chromosomes.size()<10) { //First generation is completely random
                Chromosome newChromosome = new Chromosome(items);
                if(newChromosome.getTotalWeight() <= maxWeight){
                    chromosomes.add(newChromosome);
                }
            }

            Collections.sort(chromosomes.reversed());
            System.out.println(chromosomes.getFirst());

            Random random = new Random();

            for(int i=0; i<100; i++){
                List<Chromosome> nextGen = new ArrayList<>();
                nextGen.add(chromosomes.getFirst()); //Copying the best result as-is

                int sumOfValues = 0;
                for(Chromosome chromosome: chromosomes){
                    sumOfValues += chromosome.getTotalValue();
                }

                while(nextGen.size()<10){
                    int randomValue = random.nextInt(sumOfValues);
                    int counter = 0;
                    Chromosome firstParent = new Chromosome(items);
                    Chromosome secondParent = new Chromosome(items);
                    for(Chromosome chromosome: chromosomes){
                        counter += chromosome.getTotalValue();
                        if(counter >= randomValue){
                            firstParent = chromosome;
                            break;
                        }
                    }
                    counter = 0;
                    for(Chromosome chromosome: chromosomes){
                        counter += chromosome.getTotalValue();
                        if(counter >= randomValue){
                            secondParent = chromosome;
                            break;
                        }
                    }
                    Chromosome child = new Chromosome(items, firstParent, secondParent);
                    child.mutate();
                    if (child.getTotalWeight() <= maxWeight){
                        nextGen.add(child);
                    }

                }

                chromosomes = nextGen;
                Collections.sort(chromosomes.reversed());

                System.out.println(chromosomes.getFirst());

            }

        } catch (FileNotFoundException e) {
            System.err.println("missing data");
        }
    }
}