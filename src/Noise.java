class Noise {

	public static void main(String[] args) {
		double[] noiseSeed = new double[10];
		double[] noise     = new double[10];

		for (int i=0; i<10; i++)
			noiseSeed[i] = (double)Math.random();

		noise = perlinNoise1D(noiseSeed, 1);

		for (int i=0; i<10; i++)
			System.out.println(noise[i]);
	}

	public static double[] perlinNoise1D(double[] seed, int octaves) {
		double[] out = new double[seed.length];
		for (int i=0; i<seed.length; i++) {
			double noise = 0.0;
			double scale  = 1.0;
			for (int j=0; j<octaves; j++) {
				int pitch    = (int)(seed.length/Math.pow(2,j));
				int sample1  = (i / pitch) * pitch;
				int sample2  = (sample1 + pitch) % seed.length;
				double blend  = (double)(i-sample1) / (double)pitch;
				double sample = (1.0 - blend) * seed[sample1]+blend * seed[sample2];
				noise += sample * scale;
				scale = scale / 2.0;
			}
			out[i] = noise;
		}
		return out;
	}
}
