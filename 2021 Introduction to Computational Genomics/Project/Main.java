// This project aims to search for motifs, and tries to find the consensus string with Randomized Motif Search and Gibbs Sampler.
// Authors: Ahmet Tunahan Cinsoy, Muhammet Yasin Tufan, Enes Garip
// Last Edited: 19.11.2021

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static File file;
    public static int k;
    public static double[][] profile;
    public static double[][] gibbsProfile;
    public static int randomLineIndex;
    public static int[][] countMatrix;
    public static String eliminatedLine;
    public static String atgc ="ATGC";
    public static int mostPotentialIndex;
    public static double[][] occurrenceRatioOfKMersMatrix;
    public static String[] gens = new String[10];
    public static String[] motifs = new String[10];
    public static double[][] bestProbabilities = new double[10][2];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter k value: ");

        k = input.nextInt();
        profile = new double[4][k];
        gibbsProfile = new double[4][k];
        countMatrix = new int[4][k];
        occurrenceRatioOfKMersMatrix = new double[10][500-k+1];

        createFile();
        writeToFile();
        readGens();
        printOutput();
    }

    public static int randomizedMotifSearch(int k) {
        getRandomMotif(k);
        String[] bestMotifs = motifs.clone();
        int bestScore = score(k);
        int score;
        int count = 1;
        while (true){
            getRandomMotif(k);
            String[] tempMotifs = motifs.clone();
            int tempScore = Integer.MAX_VALUE;
            while (true) {
                getProfile(k);
                getBestKMers(k);
                score = score(k);
                if (score < tempScore) {
                    tempMotifs = motifs.clone();
                    tempScore = score;
                }
                else {
                    break;
                }
            }
            if(tempScore < bestScore) {
                bestScore = tempScore;
                bestMotifs = tempMotifs.clone();
                count = 1;
            }
            else if (count % 50 == 0) {
                return bestScore;
            }
            else {
                motifs = bestMotifs.clone();
                count++;
            }
        }
    }

    public static int gibbsSampler(int k){
        getRandomMotif(k);
        int bestScore=score(k);
        int count = 1;
        while(true){
            Random random = new Random();
            randomLineIndex = random.nextInt(10);
            eliminatedLine =gens[randomLineIndex];
            gens[randomLineIndex] = "";
            getCountMatrix(k);
            applyLaplace();
            generateProfileMatrix();
            revertDeleteOperation();
            calculateKMerProbabilities();
            findProbability();
            updateMotif(k);
            int tempScore=score(k);
            if(tempScore<bestScore){
                bestScore=tempScore;
                count=1;
            }else if(count % 50==0){
                return bestScore;
            }else{
                count++;
            }
        }
    }

    public static void updateMotif(int k) {
        int index = mostPotentialIndex;
        motifs[randomLineIndex]= gens[randomLineIndex].substring(index,index+k);
    }

    public static void revertDeleteOperation() {
        gens[randomLineIndex]= eliminatedLine;
    }

    public static void findProbability() {
        for (int i = 0; i < occurrenceRatioOfKMersMatrix.length ; i++) {
            double temp = occurrenceRatioOfKMersMatrix[i][0];
            for (int j = 0; j < occurrenceRatioOfKMersMatrix[0].length; j++) {
                if(occurrenceRatioOfKMersMatrix[i][j] > temp){
                    temp = occurrenceRatioOfKMersMatrix[i][j];
                    bestProbabilities[i][1]=j;
                    if(randomLineIndex ==i){
                        mostPotentialIndex =j;
                    }
                }
            }
            bestProbabilities[i][0]=temp;
        }
    }

    public static void calculateKMerProbabilities() {
        double probabilityOfKMer;
        for (int i = 0; i < occurrenceRatioOfKMersMatrix.length ; i++) {
            for (int j = 0; j < occurrenceRatioOfKMersMatrix[0].length ; j++) {
                probabilityOfKMer=1;
                for (int t = 0; t <k ; t++) {
                    switch (gens[i].charAt(j+t)){
                        case 'A' -> probabilityOfKMer *= gibbsProfile[0][t];
                        case 'C' -> probabilityOfKMer *= gibbsProfile[1][t];
                        case 'G' -> probabilityOfKMer *= gibbsProfile[2][t];
                        case 'T' -> probabilityOfKMer *= gibbsProfile[3][t];
                    }
                }
                occurrenceRatioOfKMersMatrix[i][j] = probabilityOfKMer;
            }
        }
    }

    public static void generateProfileMatrix() {
        for (int i = 0; i <countMatrix.length; i++) {
            for (int j = 0; j < countMatrix[0].length; j++) {
                gibbsProfile[i][j]=countMatrix[i][j]*0.1;
            }
        }
    }

    public static void applyLaplace() {
        boolean zeroExist=false;
        for (int i = 0; i <countMatrix[0].length && !zeroExist ; i++) {
            for (int[] matrix : countMatrix) {
                if (matrix[i] == 0) {
                    zeroExist = true;
                    break;
                }
            }
        }
        if(zeroExist){
            for (int i = 0; i <countMatrix.length; i++) {
                for (int j = 0; j < countMatrix[0].length; j++) {
                    countMatrix[i][j]+=1;
                }
            }
        }
    }

    public static void getCountMatrix(int k) {
        countMatrix = new int[4][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < 10; j++) {
                if(j== randomLineIndex) continue;
                switch (motifs[j].charAt(i)) {
                    case 'A' -> countMatrix[0][i] += 1;
                    case 'C' -> countMatrix[1][i] += 1;
                    case 'G' -> countMatrix[2][i] += 1;
                    case 'T' -> countMatrix[3][i] += 1;
                }
            }
        }
    }

    public static void readGens(){
        try {
            Scanner reader = new Scanner(file);
            int line = 0;
            while (reader.hasNextLine()) {
                gens[line] = reader.nextLine();
                line++;
            }
            reader.close();
        }catch (Exception e){
            System.out.println("Error while reading!");
            e.printStackTrace();
        }
    }

    public static void getProfile(int k) {
        profile = new double[4][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < 10; j++) {
                switch (motifs[j].charAt(i)) {
                    case 'A' -> profile[0][i] += 1.0/10;
                    case 'C' -> profile[1][i] += 1.0/10;
                    case 'G' -> profile[2][i] += 1.0/10;
                    case 'T' -> profile[3][i] += 1.0/10;
                }

            }
        }
    }

    public static int baseToIndex(char base) {
        return "ACGT".indexOf(base);
    }

    public static char indexToBase(int i) {
        return "ACGT".charAt(i);
    }

    public static double getProbability(String KMer) {
        double probability = 1;
        for (int i = 0; i < KMer.length(); i++) {
            probability *= profile[baseToIndex(KMer.charAt(i))][i];
        }
        return probability;
    }

    public static void  getBestKMers(int k) {
        for (int i = 0; i < gens.length; i++) {
            double bestVal = -1;
            String bestKMer = "";
            for (int j = 0; j < gens[i].length() - k + 1; j++) {
                String KMer = gens[i].substring(j, j + k);
                double prob = getProbability(KMer);
                if (bestVal < getProbability(KMer)) {
                    bestKMer = KMer;
                    bestVal = prob;
                }
            }
            motifs[i] = bestKMer;
        }
    }

    public static void getRandomMotif(int k) {
        int range = 500 - k;
        Random random = new Random();
        int position;

        for (int i = 0; i < 10; i++) {
            position = random.nextInt(range);
            motifs[i] = gens[i].substring(position,position+k);
        }
    }

    public static int score(int k) {
        int score = 0;
        for (int i = 0; i < k; i++) {
            ArrayList<Integer> counts = new ArrayList<>();
            int ACount = 0, CCount = 0, GCount = 0, TCount = 0;
            for (String motif : motifs) {
                switch (motif.charAt(i)) {
                    case 'A' -> ACount++;
                    case 'C' -> CCount++;
                    case 'G' -> GCount++;
                    case 'T' -> TCount++;
                }
            }
            counts.add(ACount);
            counts.add(CCount);
            counts.add(GCount);
            counts.add(TCount);
            Collections.sort(counts);
            score += 10 - counts.get(3);
            counts.clear();
        }
        return score;
    }
    public static String findConsensus() {
        getProfile(k);
        double maxProb = 0;
        StringBuilder consensus = new StringBuilder();
        char base = ' ';
        for (int i = 0; i < profile[0].length; i++) {
            for (int j = 0; j < profile.length; j++) {
                if (profile[j][i] > maxProb) {
                    maxProb = profile[j][i];
                    base = indexToBase(j);
                }
            }
            consensus.append(base);
            maxProb = 0;
        }
        return consensus.toString();
    }

    public static ArrayList<Integer> randomPositions(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        ArrayList<Integer> randomPositions = new ArrayList<>();
        for (int i=0; i<4; i++) {
            randomPositions.add(list.get(i));
        }
        return randomPositions;
    }

    public static void createFile(){
        try {
            file = new File("DNA.txt");
            if (!file.createNewFile()) {
                System.out.println("File already exists. The program will overwrite the document!");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeToFile(){
        int k_mer=10;
        Random r = new Random();
        String[] mutatedStrings= new String[10];
        for (int i = 0; i < k_mer ; i++) {
            mutatedStrings[i]="";
            if(i!=0){
                mutatedStrings[i]=mutatedStrings[0];
                continue;
            }
            for (int j = 0; j <10 ; j++) {
                mutatedStrings[i]=mutatedStrings[i].concat(""+ atgc.charAt(r.nextInt(atgc.length())));
            }
        }
        System.out.println("Original String:" + mutatedStrings[0]);
        StringBuilder stringBuilder;

        for (int i = 0; i <10 ; i++) {
            List<Integer> mutatedPositions= randomPositions();
            stringBuilder = new StringBuilder(mutatedStrings[i]);
            for (int j = 0; j < 4 ; j++) {
                int randomBaseIndex=r.nextInt(atgc.length());
                char randomBase= atgc.charAt(randomBaseIndex);
                int randomPosition=mutatedPositions.get(j);
                if(stringBuilder.charAt(randomPosition)==randomBase){
                    randomBase= atgc.charAt((randomBaseIndex+1)%4);
                }

                stringBuilder.setCharAt(randomPosition, randomBase);
            }
            mutatedStrings[i]=stringBuilder.toString();
        }

        System.out.println("Mutated Strings:");
        for (String mutatedKMerString : mutatedStrings) {
            System.out.println("\t\t\t\t"+mutatedKMerString);
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            for (int i = 0; i < k_mer; i++) {
                int randStartingIndex = r.nextInt(500-k_mer);
                for (int j = 0; j < 500-k_mer; j++) {
                    if(j==randStartingIndex){
                        fileWriter.write(mutatedStrings[i]);
                    }
                    fileWriter.write(atgc.charAt(r.nextInt(atgc.length())));
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while write!");
            e.printStackTrace();
        }
    }

    public static void printMotifs() {
        for (String motif : motifs) {
            System.out.println("\t\t\t\t"+motif);
        }
    }

    public static void printOutput(){
        System.out.println("\n--------- RANDOMIZED MOTIF SEARCH ---------");
        System.out.println("Randomized Motif Search Score: "+randomizedMotifSearch(k));
        System.out.println("Randomized Motif Search Consensus: "+findConsensus());
        printMotifs();
        System.out.println("\n-------------- GIBBS SAMPLER --------------");
        System.out.println("Gibbs Sampler Score: "+gibbsSampler(k));
        System.out.println("Gibbs Sampler Consensus: "+findConsensus());
        printMotifs();
    }
}

