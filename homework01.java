import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class homework01 {
    public static void main(String[] args) throws IOException {
        //读入题目个数
        System.out.println("请输入要生成题目的个数");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        ArrayList<Integer>[] als = new ArrayList[n];
        //创建BufferedWriter对象来写文件
        BufferedWriter writerExe = new BufferedWriter(new FileWriter("Exercises.txt"));
        BufferedWriter writerAns = new BufferedWriter(new FileWriter("Answers.txt"));
        for (int i=0 ; i < n ; i++){
            //先创建一个随机数operatorNum，确定有几个运算符
            //0 -> 1个 , 1 -> 2个
            int operatorNum = ((int)(10 * Math.random()))%2;
            ArrayList<Integer> test = new ArrayList<>();
            int[] opes = null;
            int[] nums = null;
            int answer = 0;

            //经过多重判断条件得到100以内加减法的式子
            switch (operatorNum) {
                case 0 -> {
                    //得到两个100以内的随机数，运算结果要>=0 <=100
                    while (true) {
                        nums = getNums(2);
                        //再创建一个随机数，确定是加法还是减法
                        //0 -> + , 1 -> -
                        opes = getOpe(1);
                        if (opes[0] == 0) {
                            answer = nums[0] + nums[1];
                        } else {
                            answer = nums[0] - nums[1];
                        }
                        if (answer >= 0 && answer <= 100) {
                            break;
                        }
                    }
                    test.add(nums[0]);
                    test.add(opes[0]);
                    test.add(nums[1]);
                }
                case 1 -> {
                    //得到3个0~100随机数,通过运算得到answer
                    while (true) {
                        nums = getNums(3);
                        opes = getOpe(2);
                        if (opes[0] == 0 && opes[1] == 0) {            //+ +
                            answer = nums[0] + nums[1] + nums[2];
                        } else if (opes[0] == 0 && opes[1] == 1) {      //+ -
                            answer = nums[0] + nums[1] - nums[2];
                        } else if (opes[0] == 1 && opes[1] == 0) {      //- +
                            answer = nums[0] - nums[1] + nums[2];
                        } else if (opes[0] == 1 && opes[1] == 1) {      //- -
                            answer = nums[0] - nums[1] - nums[2];
                        }
                        if (nums[0] < nums[1] && opes[0] == 1 || nums[1] < nums[2] && opes[1] == 1) {
                            continue;
                        }
                        if (answer >= 0 && answer <= 100) {
                            break;
                        }
                    }
                    test.add(nums[0]);
                    test.add(opes[0]);
                    test.add(nums[1]);
                    test.add(opes[1]);
                    test.add(nums[2]);
                }
            }

            //判断所得的式子是否已经存在
            if (i != 0 && isRepetition(als,nums,opes)){
                i--;
                continue;
            }else {
                als[i] = test;
            }

            //如果既符合条件，又不是重复的式子，就把式子中的数字和运算符拼接成一个字符串
            String sentence = "";
            if (nums.length == 2){
                if (opes[0] == 0){
                    sentence = nums[0] + "+" + nums[1] + "=";
                }else
                    sentence = nums[0] + "-" + nums[1] + "=";
            }else if (nums.length == 3){
                if (opes[0] == 0 && opes[1] == 0){      //+ +
                    sentence = nums[0] + "+" + nums[1] + "+" + nums[2] +"=";
                }else if (opes[0] == 0 && opes[1] == 1){      //+ -
                    sentence = nums[0] + "+" + nums[1] + "-" + nums[2] +"=";
                }else if (opes[0] == 1 && opes[1] == 0){      //- +
                    sentence = nums[0] + "-" + nums[1] + "+" + nums[2] +"=";
                }else if (opes[0] == 1 && opes[1] == 1){      //- -
                    sentence = nums[0] + "-" + nums[1] + "-" + nums[2] +"=";
                }
            }

            //把拼接好的字符串写入到文件中
            writerExe.write((i+1) + "." + sentence);
            writerExe.newLine();
            String answerString = (i+1) + "."+ answer;
            writerAns.write(answerString);
            writerAns.newLine();
        }

        //循环结束，关闭文件写入流
        writerExe.close();
        writerAns.close();
    }

    public static int[] getOpe(int m){
        int[] ints = new int[m];
        for (int i=0 ; i<m ; i++){
            ints[i] = ((int)(10 * Math.random()))%2;
        }
        return ints;
    }

    public static int[] getNums(int m){
        //生成m个0~100以内的随机整数,存到一个数组中
        int[] ints = new int[m];

        for (int i=0 ; i<m ; i++){
            ints[i] = (int)(Math.random() * 100);
        }

        return ints;
    }

    public static boolean isRepetition(ArrayList<Integer>[] als,int[] nums,int[] opes){
        //遍历als数组，查找是否有重复的题目
        for (int i=0 ; als[i] != null ; i++){
            ArrayList<Integer> array = als[i];
            if (array.size() == 3){
                if (nums[0] == array.get(0) && opes[0] == array.get(1)
                        && nums[1] == array.get(2)){
                    return true;
                }
            }else if (array.size() == 5){
                if (nums[0] == array.get(0) && opes[0] == array.get(1)
                        && nums[1] == array.get(2) && opes[1] == array.get(3)
                        && nums[2] == array.get(4)){
                    return true;
                }
            }
        }
        return false;
    }
}
