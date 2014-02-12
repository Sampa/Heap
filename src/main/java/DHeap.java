// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
    // Throws UnderflowException as appropriate

import java.util.Arrays;

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    private int children;
    private static final int DEFAULT_CAPACITY = 100;
    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array

    @Override
    public String toString() {

            String str = "";
            for(int i = 1;i<currentSize;i++){
                str+=array[i]+"-";
            }
        return "DHeap{" +
                "children=" + children +
                ", currentSize=" + currentSize +
                ", array=" + Arrays.toString(array) +
                '}';
    }

    /**
     * Construct a binary heap.
     */
    public DHeap()
    {
        this(2);
    }
       /**
     * Construct the d-heap.
     * @param children the number of children for each node in the d-heap.
     *                 or throws IllegalArgumentException if num of children < 2
     */
    public DHeap(int children) throws IllegalArgumentException
    {
        if(children<2)
            throw new IllegalArgumentException("parameter children to dheap must be > 1");
        this.children = children;
        currentSize = 0;
        array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1 ];
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        if( currentSize == array.length - 1 )
               enlargeArray( array.length * children + 1 );

        // Percolate up
        int hole = ++currentSize;
        for( array[ 0 ] = x; x.compareTo( array[ hole / children ] ) < 0; hole /= children )
            array[ hole ] = array[ hole / children ];
        array[ hole ] = x;
    }

    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }

    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException();

        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin( );
        array[ 1 ] = array[ currentSize-1 ];
        percolateDown( 1 );
        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / children; i > 0; i-- )
            percolateDown( i );
    }
    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }
    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }
    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole )
    {
        int child;
        AnyType tmp = array[ hole ];
        for( ; hole * children  <= currentSize; hole = child )
        {
            child = hole * children;
            for(int i=1;i<=children;i++){
                AnyType temp = array[child+i];
//                System.out.println(temp.toString());
            }
            System.out.println(children);
            if( child != currentSize && array[ child+1].compareTo( array[ child ] ) < 0 )
                child++;
            if( array[ child ].compareTo( tmp ) < 0 )
                array[ hole ] = array[ child ];
            else
                child++;
            if( array[ child ].compareTo( tmp ) < 0 )
                array[ hole ] = array[ child ];
            else
                child++;
            if( array[ child ].compareTo( tmp ) < 0 )
                array[ hole ] = array[ child ];

        }
        System.out.print(tmp);
        array[ hole ] = tmp;
    }
    public int parentIndex(int child) throws IllegalArgumentException{
        int parentIndex = child/children;
        if(parentIndex < 1)
            throw new IllegalArgumentException();
        System.out.print(child+"/"+children+"/"+child/children);
        return child/children;
    }
    // Test program
    public static void main( String [ ] args )
    {
        int numItems = 5;
        DHeap<Integer> h = new DHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ ){
            int deleted = h.deleteMin();
            if( deleted != i )
                System.out.println(deleted+ "Oops! " + i );
        }
    }
}

