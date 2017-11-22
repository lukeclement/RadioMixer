import numpy as n
import matplotlib.pyplot as plt
#plt.style.use('classic')
plt.style.use('_classic_test')
x=n.array([5.003,4.944,4.905,4.857,4.804,4.746,4.7,4.653,4.599,4.552,4.498,4.442,4.4,4.355,4.303,4.251,4.2,4.149,4.1,4.054,4.036])
y=n.array([0.995,0.969,0.882,0.845,0.787,0.747,0.707,0.647,0.602,0.548,0.5,0.45,0.401,0.351,0.3,0.25,0.2,0.15,0.1,0.05,0.04])
err=n.array([0.018,0.011,0.016,0.023,0.024,0.02,0.015,0.013,0.012,0.012,0.003,0.004,0.001,0.004,0.003,0.001,0.001,0.001,0.001,0.001,0.001])

xFit=n.linspace(4,5.05,1000)
yFit=xFit-4

plt.title("Input frequency vs Output frequency")
plt.xlabel("Input Frequncy/MHz")
plt.ylabel("Output frequency/MHz")
plt.errorbar(x,y,yerr=err,fmt='x')
plt.plot(xFit,yFit)
plt.show();
