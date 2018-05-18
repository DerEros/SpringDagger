Pizza order service - implemented using Spring 5

# Two worlds

Spring offers many ways to implement software. I will focus on two:
- Decentralized Config:  
Putting Spring annotation as close to the target class as possible, use central
configuration classes only where necessary. The benefit of this method is having all the
configuration close to where it is applied. No need to go looking in other classes.
- Centralized Config:  
Putting Spring annotations in a central location, e.g. in central configuration classes
and avoiding annotation in production code where possible. No pollution of the whole
code base, less vendor lock-in (but still some).

The world is not black and white. Neither of the above approaches will be implemented
at 100%. However, a difference should still be visible.
