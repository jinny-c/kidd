package com.kidd.test.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description 排序
 * 快速、归并、堆
 * @auth chaijd
 * @date 2022/1/7
 */
@Slf4j
public class SortingTest {

    @Test
    public void test(){
        int[] a = {11,0,2,6,8,4,10,1,3,4,5,9};
        System.out.println("排序前：" + Arrays.toString(a));
        quickSort(a);
        System.out.println("排序后：" + Arrays.toString(a));
    }

    /**
     * 快速排序
     *
     *     选择一个基准元素，通常选择第一个元素或者最后一个元素
     *     通过一趟排序将待排序的记录分割成独立的两部分，其中一部分记录的元素值均比基准元素值小。另一部分记录的元素值均比基准值大
     *     此时基准元素在其排好序后的正确位置
     *     然后分别对这两部分记录用同样的方法继续进行排序，直到整个序列有序。
     *
     * @param a
     */
    public void quickSort(int[] a) {
        quickSortInternally(a, 0, a.length - 1);
    }
    /**
     * 递归函数
     * @param arr
     * @param low
     * @param high
     */
    private void quickSortInternally(int[] arr, int low, int high) {
        //递推终止条件
        if (low >= high) {
            return;
        }
        //分成两个部分
        int pivot = partition(arr, low, high);
        //递归左边
        quickSortInternally(arr, low, pivot - 1);
        //递归右边
        quickSortInternally(arr, pivot + 1, high);
    }
    /**
     * 分区函数
     *
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private int partition(int[] arr, int low, int high) {
        //选择一个数据作为分区点pivot
        //枢轴记录
        int pivot = arr[low];
        while (low < high) {
            //从右侧--，拿到小于pivot的
            while (low < high && arr[high] >= pivot) {
                --high;
            }
            //交换比枢轴小的记录到 左端
            arr[low] = arr[high];
            //log.info("{},{},{}", low, high, Arrays.toString(arr));
            //从左侧++，拿到大于pivot的
            while (low < high && arr[low] <= pivot) {
                ++low;
            }
            //交换比枢轴大的记录到 右端
            arr[high] = arr[low];
            //log.info("{},{},{}", low, high, Arrays.toString(arr));
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //此时 小于pivot的[low]在左边，大于pivot的在[low]右边
        //log.info("end--{},{},{}", low, high, Arrays.toString(arr));
        //返回 枢轴的位置
        return low;
    }

    /**
     * 两路归并排序算法
     *
     * 分而治之(divide - conquer);每个递归过程涉及三个步骤
     * 第一, 分解: 把待排序的 n 个元素的序列分解成两个子序列, 每个子序列包括 n/2 个元素.
     * 第二, 治理: 对每个子序列分别调用归并排序MergeSort, 进行递归操作
     * 第三, 合并: 合并两个排好序的子序列,生成排序结果.
     *
     * @param a
     * @param low
     * @param high
     * @return
     */
    public static int[] sort(int[] a,int low,int high){
        int mid = (low+high)/2;
        if(low<high){
            sort(a,low,mid);
            sort(a,mid+1,high);
            //左右归并
            mergeSort(a,low,mid,high);
        }
        return a;
    }

    public static void mergeSort(int[] a, int low, int mid, int high) {
        int[] temp = new int[high-low+1];
        int i= low;
        int j = mid+1;
        int k=0;
        // 把较小的数先移到新数组中
        while(i<=mid && j<=high){
            if(a[i]<a[j]){
                temp[k++] = a[i++];
            }else{
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while(i<=mid){
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while(j<=high){
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for(int x=0;x<temp.length;x++){
            a[x+low] = temp[x];
        }
    }




    /**
     * 快速排序
     *
     * 1.取出数组第0个数据
     * 2.从数组最右边开始遍历，如果遍历位置的数据比第0个位置的数据小，将该位置的数据赋值给左边指针停留下的位置。
     * 3.改变遍历方向，从左边开始开始遍历，如果发现左边的数据比第0个位置的数据大，将该位置的数据赋值给2步骤停留下来的位置，并变换方向。
     * 4.循环2、3步骤直到左右遍历到的下标重合
     * 5.将取出的第0个位置的值赋值给循环结束后左右指针停留下的位置
     *
     * @param array
     * @param start
     * @param end
     */
    private void quickSort(Integer[] array, Integer start, Integer end) {
        if (start >= end) {
            return;
        }
        int key = array[start];
        int left = start;
        int right = end;
        boolean direction = true;
        L1:
        while (left < right) {
            if (direction) {
                for (int i = right; i > left; i--) {
                    if (array[i] < key) {
                        array[left++] = array[i];
                        right = i;
                        direction = !direction;
                        continue L1;
                    }
                }
                right = left;
            } else {
                for (int i = left; i < right; i++) {
                    if (array[i] > key) {
                        array[right--] = array[i];
                        left = i;
                        direction = !direction;
                        continue L1;
                    }
                }
                left = right;
            }
        }
        array[left] = key;
        quickSort(array, start, left - 1);
        quickSort(array, left + 1, end);
    }

    @Test
    public void testQuickSort() {
        Integer[] array = new Integer[]{1, 3, 4, 10, 2, 5, 6, 9, 7, 8};
        quickSort(array, 0, array.length - 1);
        List arrList = new ArrayList<>(Arrays.asList(array));
        //Collections.addAll(arrList, array);
        System.out.println(arrList);
//        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i]);
//        }
    }

    /**
     * 归并排序
     *
     * 归并排序是将目标数组分成左右两个数组，左右两个数组必须是有序的，然后对这两个数组合并从而实现排序。
     * 对于任意的数组都可以将所有的数据分成若干个数组，每个数组中都只有一个元素，然后两两合并。（因此，归并排序的内存开销会比快速排序多）
     *
     * @param array
     * @param left
     * @param right
     */
    private void mergeSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) >> 1;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid + 1, right);
    }

    private void merge(int[] array, int left, int mid, int right) {
        int leftSize = mid - left;
        int rightSize = right - mid + 1;
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];
        System.arraycopy(array, left, leftArray, 0, leftSize);
        System.arraycopy(array, mid, rightArray, 0, rightSize);
        int index=left;
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex<leftSize&&rightIndex<rightSize){
            if(leftArray[leftIndex]<rightArray[rightIndex]){
                array[index++] = leftArray[leftIndex++];
            }else {
                array[index++] = rightArray[rightIndex++];
            }
        }
        while (leftIndex<leftSize){
            array[index++] = leftArray[leftIndex++];
        }
        while (rightIndex<rightSize){
            array[index++] = rightArray[rightIndex++];
        }
    }
    @Test
    public void testMergeSort() {
        //TODO int数组跟 Integer数组 区别
        int[] array = new int[]{1, 3, 4, 10, 2, 5, 6, 9, 7, 8};
        mergeSort(array, 0, array.length - 1);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

}
