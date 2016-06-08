f = fopen('values.txt', 'r');
A = fscanf(f, '%f %f %f', [3 Inf]);
fclose(f);

x = A(1, :)';
y = A(2, :)';
r = A(3, :)';

y = y + 0.037;

y = y.*1000;
r = r.*1000;


######
# y
######

x2 = [x; -30.1];
y2 = [y; y(length(y))];

x2 = [0.1; x2];
y2 = [y2(1); y2];

plot(x2, y2, 'or')
hold on

degree = 8;

X = [];

for i = 0 : degree
  X = [X x2.^i];
end

[Q, R] = qr(X);
ay = R\(Q'*y2);

xplot = [linspace(-30.1, 0.1, 1000)]';

yplot = 0;
for i = 1 : length(ay)
  yplot += ay(i) * xplot.^(i-1);
end

plot(xplot, yplot, 'b')

######
# r
######
x2 = [x; -31.0];
r2 = [r; 1000];

x2 = [1; x2];
r2 = [1000; r2];

plot(x2, r2, 'oy')
hold on

degree = 4;

X = [];

for i = 0 : degree
  X = [X x2.^i];
end

w = [linspace(1, 1, 9)]';
w(1) = 1.5;
w(2) = 6;
w(length(w)) = 1.5;
w(length(w)-1) = 2;
W = diag(w);

ar = (X'*W*X)\(X'*W*r2);

xplot = [linspace(-31, 1, 1000)]';

rplot = 0;
for i = 1 : length(ar)
  rplot += ar(i) * xplot.^(i-1);
end

plot(xplot, rplot, 'g')
hold off

ay
ar