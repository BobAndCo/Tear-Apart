class Noise {

	int width, height;

	Noise(int w, int h) {
		width  = w;
		height = h;
	}

	public double[] makeSeed(int dimension) {
		int a;
		if (dimension == 2) {
			a = height * width;
		} else {
			a = width;
		}
		double[] seed = new double[a];
		for (int i=0; i < a; i++) {
			seed[i] = Math.random();
		}
		return seed;
	}

	public double[] perlinNoise1D(double[] seed, int octaves, double bias) {
		int count = seed.length;
		double[] output = new double[count];
		for (int x=0; x < count; x++) {
			double noise = 0.0;
			double scaleAccumilator = 0.0;
			double scale = 1.0;

			for (int o = 0; o < octaves; o++) {
				int pitch = count >> o;
				int sample1 = (x / pitch) * pitch;
				int sample2 = (sample1 + pitch) % count;

				double blend = (float)(x - sample1) / (float)pitch;
				double sample = (1.0 - blend) * seed[sample1] + blend * seed[sample2];

				scaleAccumilator += scale;
				noise += sample * scale;
				scale = scale/bias;
			}
			output[x] = noise / scaleAccumilator;
		}
		return output;
	}

	public double[] perlinNoise2D(double[] seed, int octaves, double bias) {
		double[] output = new double[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double noise = 0.0;
				double scaleAccumilator = 0.0;
				double scale = 1.0;

				for (int o = 0; o < octaves; o++) {
					int pitch = width >> o;
					int sampleX1 = (x / pitch) * pitch;
					int sampleY1 = (y / pitch) * pitch;
					
					int sampleX2 = (sampleX1 + pitch) % width;
					int sampleY2 = (sampleY1 + pitch) % width;

					double blendX = (double)(x - sampleX1) / (double)(pitch);
					double blendY = (double)(y - sampleX1) / (double)(pitch);

					double sampleT = (1.0-blendX)*seed[sampleY1*width+sampleX1]+blendX*seed[sampleY1*width+sampleX2];
					double sampleB = (1.0-blendX)*seed[sampleY2*width+sampleX1]+blendX*seed[sampleY2*width+sampleX2];

					scaleAccumilator += scale;
					noise += (blendY * (sampleB - sampleT) + sampleT) * scale;
					scale = scale / bias;
				}

				output[y * width + x] = noise / scaleAccumilator;
			}
		}
		return output;
	}

}
