# coding-challenge-app

Question & Answer

Q1. What is the difference between abstract and interface?

Ans. Main difference is methods of a Java interface are implicitly abstract and cannot have implementations. A Java abstract class can have instance methods that implements a default behavior. 
2.Variables declared in a Java interface is by default final. An abstract class may contain non-final variables.

Q2. Why is Java 7’s class inheritance flawed?

Ans. 

Because in java ambiguity not hendel for multiple inheritance.

abstract class SuperType1 {
    protected int item;
}

interface SuperType2{
    final float item=2.0;
}
public class SubType extends SuperType1 implements SuperType2{
    public static void main(String[] args){
        SubType s = new SubType(); 
        //s.item = 2; //Compiler knows that 'item' is only mutable in 'class SuperType1', this code should not fail.
    }
}

i get ambiguous error in java 7, all fields in interface are public/static/final. So, in addition to being static, it is not mutable, so java has to know at compile time that s.item is from SuperType1.looks like, java 8 has introduced lot of contradictory concepts like having method implementations in interface{}.java 8 will make java programmer change his design approach in making use of interface and abstract class.
it does not support multiple inheritance.


Q3. What are the major differences between Activities and Fragments ?

Ans. Activity is an application component that provides a screen, with which users can interact in order to do something.
Whereas a Fragment represents a behavior or a portion of user interface in an Activity.

Q4. When using Fragments , how do you communicate back to their hosting Activity ?

Ans. Communication between activity and fragment are achieve through interface.

Example of Fragment to Activity communication:

public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	
	 @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mCallback.onArticleSelected(position);
    }

    ...
}

Activity Code.
public static class MainActivity extends Activity
        implements HeadlinesFragment.OnHeadlineSelectedListener{
    ...

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
    }
}

Another way.
From fragment to activty:
((YourActivityClassName)getActivity()).yourPublicMethod();

From activity to fragment:

FragmentManager fm = getSupportFragmentManager();

//if you added fragment via layout xml
YourFragmentClass fragment = (YourFragmentClass)fm.findFragmentById(R.id.your_fragment_id);
fragment.yourPublicMethod();


Q5. Can you make an entire app without ever using Fragments ? Why or why not?
Are there any special cases when you absolutely have to use or should use
Fragments?

Ans. yes if application not use map. In the case of map application should use fragment. 

Q6 What makes an AsyncTask such an annoyance to Android developers? Detail
some of the issues with AsyncTask , and how to potentially solve them.

Ans
Because the process (doInBackground()) runs until it is finished that genrate some issues listed below.

1 AsyncTask and Rotation.
The most common use case is to have an AsyncTask run a time-consuming operation that updates a portion of the UI when it’s completed (in AsyncTask.onPostExecute()).
This works great… until you rotate the screen. When an app is rotated, the entire Activity is destroyed and recreated. When the Activity is restarted, your AsyncTask’s reference to the Activity is invalid, so onPostExecute() will have no effect on the new Activity.This can be confusing if you are implicitly referencing the current Activity by having AsyncTask as an inner class of the Activity.

The usual solution to this problem is to hold onto a reference to AsyncTask that lasts between configuration changes, which updates the target Activity as it restarts. There are a variety of ways to do this, though they either boil down to using a global holder (such as in the Application object) or passing it through Activity.onRetainNonConfigurationInstance(). For a Fragment-based system, you could use a retained Fragment (via Fragment.setRetainedInstance(true)) to store running AsyncTasks.

2 AsyncTasks and the Lifecycle
Along the same lines as above, it is a misconception to think that just because the Activity that originally spawned the AsyncTask is dead, the AsyncTask is as well. It will continue running on its merry way even if you exit the entire application. The only way that an AsyncTask finishes early is if it is canceled via AsyncTask.cancel().

This means that you have to manage the cancellation of AsyncTasks yourself; otherwise you run the risk of bogging down your app with unnecessary background tasks, or of leaking memory. When you know you will no longer need an AsyncTask, be sure to cancel it so that it doesn’t cause any headaches later in the execution of your app.

3 Cancelling AsyncTasks
Suppose you’ve got a search query that runs in an AsyncTask. The user may be able to change the search parameters while the AsyncTask is running, so you call AsyncTask.cancel() and then fire up a new AsyncTask for the next query. This seems to work… until you check the logs and realize that your AsyncTasks all ran till completion, regardless of whether you called cancel() or not! This even happens if you pass mayInterruptIfRunning as true – what’s going on?

The problem is that there’s a misconception about what AsyncTask.cancel() actually does. It does not kill the Thread with no regard for the consequences! All it does is set the AsyncTask to a “cancelled” state. It’s up to you to check whether the AsyncTask has been canceled so that you can halt your operation. As for mayInterruptIfRunning – all it does is send an interrupt() to the running Thread. In the case that your Thread is uninterruptible, then it won’t stop the Thread at all.

There are two simple solutions that cover most situations: Either check AsyncTask.isCancelled() on a regular basis during your long-running operation, or keep your Thread interruptible. Either way, when you call AsyncTask.cancel() these methods should prevent your operation from running longer than necessary.

This advice doesn’t always work, though – what if you’re calling a long-running method that is uninterruptible (such as BitmapFactory.decodeStream())? The only success I’ve had in this situation is to create a situation which causes an Exception to be thrown (in this case, prematurely closing the stream that BitmapFactory was using). This meant that cancel() alone wouldn’t solve the problem – outside intervention was required.

 4 Limitations on Concurrent AsyncTasks
There are some limitations on the number of AsyncTasks that you can start. The modern AsyncTask is limited to 128 concurrent tasks, with an additional queue of 10 tasks (if supporting Android 1.5, it’s a limit of ten tasks at a time, with a maximum queue of 10 tasks). That means that if you queue up more than 138 tasks before they can complete, your app will crash. Most often I see this problem when people use AsyncTasks to load Bitmaps from the net.

If you are finding yourself running up against these limits, you should start by rethinking your design that calls for so many background threads. Alternatively, you could setup a more intelligent queue for your tasks so that you’re not starting them all at once. If you’re desperate, you can grab a copy of AsyncTask and adjust the pool sizes in the code itself.