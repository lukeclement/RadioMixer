import numpy as n
import matplotlib.pyplot as plt
#plt.style.use('classic')
plt.style.use('_classic_test')
x=n.array([5.003,4.944,4.905,4.857,4.804,4.746,4.7,4.653,4.599,4.552,4.498,4.442,4.4,4.355,4.303,4.251,4.2,4.149,4.1,4.054,4.036])
y=n.array([0.654,0.712,0.711,0.812,0.882,0.977,1.094,1.254,1.411,1.663,1.924,2.349,2.984,3.738,4.729,6.226,8.444,11.959,17.608,25.488,27.084])
err=n.array([0.036,0.025,0.024,0.026,0.027,0.036,0.035,0.043,0.056,0.041,0.058,0.042,0.077,0.082,0.083,0.166,0.155,0.298,0.371,0.501,0.48])
y=1/(n.sqrt(y))


plt.title("Input frequency vs conversion effectiveness")
plt.xlabel("Input Frequncy/MHz")
plt.ylabel("Effectiveness/%")
plt.errorbar(x,y,yerr=err,fmt='x')
plt.show();
