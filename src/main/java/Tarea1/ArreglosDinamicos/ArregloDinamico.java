package Tarea1.ArreglosDinamicos;

import java.util.Arrays;

public class ArregloDinamico<T> {
    private T[] array;
    private int lastIndex;

    public ArregloDinamico() {
        this.array = (T[]) new Object[1];
        this.lastIndex=0;
    }
    public void add(T t){
        if (this.lastIndex==array.length){
            T[] aux=this.array;
            this.array= (T[]) new Object[this.lastIndex+1];
            for (int i = 0; i < aux.length; i++) {
                array[i]=aux[i];
            }
        }
        array[lastIndex]=t;
        this.lastIndex++;
    }

    public boolean deleteLast(){
        if (this.lastIndex==0){
            return false;
        }
        T[] aux=this.array;
        this.array=(T[]) new Object[this.lastIndex-1];
        for (int i = 0; i < this.array.length; i++) {
            this.array[i]=aux[i];
        }
        this.lastIndex--;
        return true;
    }

    @Override
    public String toString() {
        return "ArregloDinamico{" +
                "array=" + Arrays.toString(array) +
                '}';
    }
}
