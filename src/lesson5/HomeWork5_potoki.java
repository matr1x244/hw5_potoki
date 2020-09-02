package lesson5;


import java.util.Arrays;

public class HomeWork5_potoki {

    private static final int size = 10000000;
    private static final int h = size / 2;
    
    public static void main(String[] args){
        HomeWork5_potoki hw5 = new HomeWork5_potoki ();
        hw5.methodOne();
        hw5.methodTwo();
    }

    private void methodOne(){
        System.out.println("запуск метода 1");
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);
        long start = System.currentTimeMillis(); // время
        for(int i = 0; i < arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("конец метода one. Время выполнения %s", String.valueOf(end - start)));
    }

    private void methodTwo(){
        System.out.println("запуск метода 2");
        float[] arr = new float[size];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        Arrays.fill(arr, 1.0f);
        long start = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        long split = System.currentTimeMillis(); //
        System.out.println(String.format("Время разделения массива %s", String.valueOf(split - start)));

        Thread thread1 = new Thread(() ->this.methodTwoIntern(arr1, 1)); // поток 1
        Thread thread2 = new Thread(() ->this.methodTwoIntern(arr2, 2)); // поток 2

        thread1.start(); // старт потока 1
        thread2.start(); // старт потока 2

        try{
            thread1.join(); //хаотично кто успел выполнять поток
            thread2.join();
        } catch (InterruptedException e){
            System.out.println(String.format("Исключение в потоках. %s", e.getMessage()));
        }


        long connect = System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr, 0, h); // копирование данных из одного в другой массив
        System.arraycopy(arr2, 0, arr, h, h);
        long end = System.currentTimeMillis();
        System.out.println(String.format("Время склейки массива %s", String.valueOf(end - connect))); // string valueof возвращает соответствующий числовой объект
        System.out.println(String.format("конец метода Two. Время выполнения %s", String.valueOf(end - start)));
    }

    private void methodTwoIntern(float[] arr, int n){
        long start = System.currentTimeMillis();
        for(int i = 0; i < arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("Время выполнния потока %d = %s", n, String.valueOf(end - start)));
    }

}
