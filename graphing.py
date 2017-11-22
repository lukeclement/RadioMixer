import numpy as n
import matplotlib.pyplot as plt
#plt.style.use('classic')
plt.style.use('_classic_test')
x=n.array([0,1,15,45,65,75,85])
y=n.array([2358,796,12265,52702,74708,126083,179363])

xFit=n.linspace(0,85,10000)
f=xFit*210000
fI=xFit
yFit=f*n.exp(-fI)



plt.title("Input frequency vs conversion effectiveness")
plt.xlabel("Input Frequncy/MHz")
plt.ylabel("Effectiveness/%")
plt.errorbar(x,y,yerr=1,fmt='x')
plt.plot(xFit,yFit)
plt.show();
