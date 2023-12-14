************************************************************************
file with basedata            : mm14_.bas
initial value random generator: 1568216103
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  12
horizon                       :  91
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     10      0       20        7       20
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          2           5   9
   3        3          2           9  11
   4        3          1           7
   5        3          2           6  11
   6        3          1           8
   7        3          3           9  10  11
   8        3          1          10
   9        3          1          12
  10        3          1          12
  11        3          1          12
  12        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     4       5    0    4    5
         2     8       5    0    2    4
         3     8       4    0    3    4
  3      1     5      10    0    9    5
         2     9       0    7    8    4
         3     9       5    0    8    4
  4      1     6      10    0    5    7
         2     7       0    8    5    6
         3    10      10    0    5    5
  5      1     3       0    2    9   10
         2     7       5    0    8    8
         3    10       0    2    6    8
  6      1     2       0    7   10    6
         2     6       0    7   10    5
         3     9       0    5   10    4
  7      1     3       0    2    8    7
         2     5       0    2    5    5
         3     8       0    2    5    3
  8      1     6      10    0    9    4
         2     8       7    0    8    3
         3    10       0    3    4    3
  9      1     6       2    0    6    8
         2     6       0    8    7    7
         3    10       0    2    2    6
 10      1     5       0    8    4    5
         2     5       1    0    4    4
         3     9       1    0    2    4
 11      1     2       4    0    6    9
         2     4       0    8    4    6
         3     8       1    0    1    5
 12      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   15   12   58   56
************************************************************************
