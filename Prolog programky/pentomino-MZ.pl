%%% Zapoctak, Neproceduralni programovani 2015/16
%%% Marek Židek
%%% Pentomino

/*** otocene a osova soumernost jsou stejne ***/

% kazdy obrazec muze byt otocen a i prevracen, jsou ukladany jako seznam ctvercu relativne vzhledem k levemu hornimu ctverci, pricemz levy > horni
% muze byt az 8 moznosti otoceni a prevraceni

obrazec('F').
obrazec('I').
obrazec('L').
obrazec('N').
obrazec('P').
obrazec('T').

obrazec('U').
obrazec('V').
obrazec('W').
obrazec('X').
obrazec('Y').
obrazec('Z').

/*   00   00     0     0     0      0    0    0
*   00     00   00     00   000   000   000   000
*    0     0     00   00      0    0    0      0
*/
variantyObrazce('F', [-1,1, -1,2,  0,1, 1,1],
           [ 0,1,  1,1,  1,2, 2,1],
           [-1,1,  0,1,  2,1, 2,2],
           [-2,1, -1,1, -1,2, 0,1],
           [-1,1,  0,1,  0,2, 1,2],
           [-1,2,  0,1,  0,2, 1,1],
           [-1,1,  0,1,  0,2, 2,0],
           [ 1,0,  1,1,  1,2, 2,1]).



%  0   00000
%  0
%  0
%  0
%  0

% x znaci vyplnovy symbol
variantyObrazce('I',[1,0, 2,0, 3,0, 4,0],
           	    [0,1, 0,2, 0,3, 0,4],
           		x,x,x,x,x,x).


%% a tak dale pro vsechny v poradi tak, jak je na ceske wikipedii Pentomino
variantyObrazce('L',[ 1,0,  2,0,  3,0, 3,1],
           [-3,1, -2,1, -1,1, 0,1],
           [ 0,1,  1,0,  2,0, 3,0],
           [ 0,1,  1,1   2,1, 3,1],
           [ 0,1,  0,2,  0,3, 1,0],
           [ 1,0,  1,1,  1,2, 1,3],
           [ 0,1,  0,2,  0,3, 1,3],
           [-1,3,  0,1   0,2, 0,3]).



 

variantyObrazce('N',[ 1,0,  1,1,  2,1, 3,1],
           [-1,1,  0,1,  1,0, 2,0],
           [-2,1, -1,1,  0,1, 1,0],
           [ 1,0,  2,0,  2,1, 3,1],
           [-1,2, -1,3,  0,1, 0,2],
           [ 0,1,  0,2,  1,2, 1,3],
           [ 0,1,  1,1,  1,2, 1,3],
           [-1,1, -1,2, -1,3, 0,1]).





variantyObrazce('P',[ 0,1,  1,0, 1,1, 2,0],
           [ 0,1,  1,0, 1,1, 2,0],
           [ 1,0,  1,1, 2,0, 2,1],
           [-1,1,  0,1, 1,0, 1,1],
           [ 0,1,  0,2, 1,1, 1,2],
           [-1,1, -1,2, 0,1, 0,2],
           [ 0,1,  0,2, 1,0, 1,1],
           [ 0,1,  1,0, 1,1, 1,2]).





variantyObrazce('T',[ 0,1,  0,2, 1,1, 2,1],
           [-2,1, -1,1, 0,1, 0,2],
           [-1,2,  0,1, 0,2, 1,2],
           [ 1,0,  1,1, 1,2, 2,0],
           x,x,x,x).




variantyObrazce('U',[0,2, 1,0, 1,1, 1,2],
           [0,1, 0,2, 1,0, 1,2],
           [0,1, 1,0, 2,0, 2,1],
           [0,1, 1,1, 2,0, 2,1],
           x,x,x,x).




variantyObrazce('V',[ 1,0,   2,0, 2,1, 2,2],
           [-2,-2, -1,2, 0,1, 0,2],
           [ 0,1,   0,2, 1,0, 2,0],
           [ 0,1,   0,2, 1,2, 2,2],
           x,x,x,x).





variantyObrazce('W',[ 1,0,  1,1,  2,1, 2,2],
           [-2,2, -1,1, -1,2, 0,1],
           [-1,1, -1,2,  0,1, 1,0],
           [ 0,1,  1,1,  1,2, 2,2],
           x,x,x,x).




variantyObrazce('X',[-1,1, 0,1, 0,2, 1,1],
           x,x,x,x,x,x,x).



 

variantyObrazce('Y',[ 1,0,  1,1, 2,0, 3,0],
           [-1,1,  0,1, 1,1, 1,2],
           [ 1,0,  2,0, 2,1, 3,0],
           [-2,1, -1,1, 0,1, 1,1],
           [ 0,1,  0,2, 0,3, 1,2],
           [-1,2,  0,1, 0,2, 0,3],
           [ 0,1,  0,2, 0,3, 1,1],
           [-1,1,  0,1, 0,2, 0,3]).





variantyObrazce('Z',[ 0,1,  1,1,  2,1, 2,2],
           [-2,1, -2,2, -1,1, 0,1],
           [-1,-2, 0,1,  0,2, 1,0],
           [ 1,0,  1,1,  1,2, 2,2],
           x,x,x,x).

main :- repeat,
		write('Resic pentomino'), nl,
		write('Vyber velikost plochy'), nl,
		write(' 1. 6x10'),nl,
		write(' 2. 5x12'),nl,
		write(' 3. 4x15'),nl,
		write(' 4. 3x20'),nl,
		get0(X),write('cool'),nl,
		integer(X), write(X), nl, X>0, X<5, 
		write('hovenko'),
		solve(obrazec(O), plocha(X), Vysledek).

read_int(N):- 		read_int(N,0).
read_int(N,N1):-	get0(C),
			((C = 10;C=13), N is N1,!
			;
			C >= "0", C =< "9", N2 is N1*10 + C - "0", read_int(N,N2),!
			).


