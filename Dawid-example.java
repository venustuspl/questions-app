
	interface ValidationInput {
		String getTextToValidate();
	}

	class Question implements ValidationInput {

		String body;

		@Override
		public String getTextToValidate() {
			return body;
		}
	}

	interface Validator {
		List<String> validate(String input);
	}

	 class LengthValidator implements Validator {
		@Override
		public List<String> validate(String input) {
			List<String> errors = new ArrayList<>();

			if (input.length() < 1) {
				errors.add("Too short");
			}
			if (input.length() > 100) {
				errors.add("Too long");
			}

			return errors;
		}
	}

	 class ValidationResult {
		boolean isValid;
		List<String> result;

		public ValidationResult(boolean isValid, List<String> result) {
			this.isValid = isValid;
			this.result = result;
		}
	}

	 class ValidationEngine {

		static List<Validator> validators;

		{
			validators.add(new LengthValidator());
		}
		
		public ValidationResult validate(ValidationInput input) {
			List<String> errors = new ArrayList<>();
			
			validators.forEach( validator -> {
				errors.addAll(validator.validate(input.getTextToValidate()));
			});
			
			ValidationResult result = new ValidationResult(errors.isEmpty(), errors);	
			
			return result;
		}
	}
