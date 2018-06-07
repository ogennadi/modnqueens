"""
  OR-tools solution to the N-queens problem.
"""
from __future__ import print_function
import sys
from ortools.constraint_solver import pywrapcp

def main(board_size):
  # Creates the solver.
  solver = pywrapcp.Solver("n-queens")
  # Creates the variables.
  # The array index is the column, and the value is the row.
  queens = [solver.IntVar(0, board_size - 1, "x%i" % i) for i in range(board_size)]
  # Creates the constraints.

  # All rows must be different.
  solver.Add(solver.AllDifferent(queens))

  # All columns must be different because the indices of queens are all different.

  # No two queens can be on the same diagonal.
  solver.Add(solver.AllDifferent([queens[i] + i for i in range(board_size)]))
  solver.Add(solver.AllDifferent([queens[i] - i for i in range(board_size)]))

  # No 3 queens are collinear
  q = queens
  for i in range(0, board_size-2):
    for j in range(i+1, board_size-1):
      for k in range(j+1, board_size):
        solver.Add( (q[i]*(j-k)) + (q[j]*(k-i)) + (q[k]*(i-j)) != 0 )

  db = solver.Phase(queens,
                    solver.CHOOSE_FIRST_UNBOUND,
                    solver.ASSIGN_MIN_VALUE)
  solver.NewSearch(db)

  # Iterates through the solutions, displaying each.

  num_solutions = 0

  while solver.NextSolution(): 
    # Display solution compactly
    for q in queens:
      sys.stdout.write( str(q.Value()) )
      sys.stdout.write( " " )
    print("")
    num_solutions += 1





# By default, solve the 8x8 problem.
board_size = 8

if __name__ == "__main__":
  if len(sys.argv) > 1:
    board_size = int(sys.argv[1])
  main(board_size)
